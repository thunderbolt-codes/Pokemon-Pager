package dev.thunderbolt.pokemonpager.data.remote

import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.apollographql.apollo3.exception.ApolloException
import dev.thunderbolt.pokemonpager.data.local.PokemonDatabase
import dev.thunderbolt.pokemonpager.data.local.PokemonEntity
import dev.thunderbolt.pokemonpager.data.local.RemoteKeyEntity
import dev.thunderbolt.pokemonpager.data.mapper.toPokemonEntity
import javax.inject.Inject

class PokemonRemoteMediator @Inject constructor(
    private val pokemonDatabase: PokemonDatabase,
    private val pokemonApi: PokemonApi,
) : RemoteMediator<Int, PokemonEntity>() {

    private val REMOTE_KEY_ID = "pokemon"

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonEntity>,
    ): MediatorResult {
        return try {
            val offset = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    // RETRIEVE NEXT OFFSET FROM DATABASE
                    val remoteKey = pokemonDatabase.remoteKeyDao.getById(REMOTE_KEY_ID)
                    if (remoteKey == null || remoteKey.nextOffset == 0) // END OF PAGINATION REACHED
                        return MediatorResult.Success(endOfPaginationReached = true)
                    remoteKey.nextOffset
                }
            }
            // MAKE API CALL
            val apiResponse = pokemonApi.getPokemonList(
                offset = offset,
                limit = state.config.pageSize,
            )
            val results = apiResponse?.results ?: emptyList()
            val nextOffset = apiResponse?.nextOffset ?: 0
            // SAVE RESULTS AND NEXT OFFSET TO DATABASE
            pokemonDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    // IF REFRESHING, CLEAR DATABASE FIRST
                    pokemonDatabase.pokemonDao.clearAll()
                    pokemonDatabase.remoteKeyDao.deleteById(REMOTE_KEY_ID)
                }
                pokemonDatabase.pokemonDao.insertAll(
                    results.mapNotNull { it?.toPokemonEntity() }
                )
                pokemonDatabase.remoteKeyDao.insert(
                    RemoteKeyEntity(
                        id = REMOTE_KEY_ID,
                        nextOffset = nextOffset,
                    )
                )
            }
            // CHECK IF END OF PAGINATION REACHED
            MediatorResult.Success(endOfPaginationReached = results.size < state.config.pageSize)
        } catch (e: ApolloException) {
            MediatorResult.Error(e)
        }
    }
}
