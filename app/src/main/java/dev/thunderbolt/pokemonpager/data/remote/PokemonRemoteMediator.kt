package dev.thunderbolt.pokemonpager.data.remote

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.apollographql.apollo3.exception.ApolloException
import dev.thunderbolt.pokemonpager.data.local.PokemonDatabase
import dev.thunderbolt.pokemonpager.data.local.PokemonEntity
import dev.thunderbolt.pokemonpager.data.mapper.toPokemonEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PokemonRemoteMediator @Inject constructor(
    private val pokemonDatabase: PokemonDatabase,
    private val pokemonApi: PokemonApi,
    private val dataStore: DataStore<Preferences>,
) : RemoteMediator<Int, PokemonEntity>() {

    private val NEXT_OFFSET = intPreferencesKey("next_offset")

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonEntity>,
    ): MediatorResult {
        return try {
            val offset = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    // RETRIEVE NEXT OFFSET FROM DATA STORE
                    val nextOffset =
                        dataStore.data.map { preferences -> preferences[NEXT_OFFSET] ?: 0 }.first()
                    if (nextOffset == 0) // END OF PAGINATION REACHED
                        return MediatorResult.Success(endOfPaginationReached = true)
                    nextOffset
                }
            }
            // MAKE API CALL
            val apiResponse = pokemonApi.getPokemonList(
                offset = offset,
                limit = state.config.pageSize,
            )
            val results = apiResponse?.results ?: emptyList()
            val nextOffset = apiResponse?.nextOffset ?: 0
            // SAVE NEXT OFFSET TO DATA STORE
            dataStore.edit { preferences ->
                preferences[NEXT_OFFSET] = nextOffset
            }
            // SAVE RESULTS TO DATABASE
            pokemonDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    // IF REFRESHING, CLEAR DATABASE FIRST
                    pokemonDatabase.dao.clearAll()
                }
                pokemonDatabase.dao.insertAll(results.mapNotNull { it?.toPokemonEntity() })
            }
            // CHECK IF END OF PAGINATION REACHED
            MediatorResult.Success(endOfPaginationReached = results.size < state.config.pageSize)
        } catch (e: ApolloException) {
            MediatorResult.Error(e)
        }
    }
}
