package dev.thunderbolt.pokemonpager.data.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.apollographql.apollo3.exception.ApolloException
import dev.thunderbolt.pokemonpager.data.local.PokemonDatabase
import dev.thunderbolt.pokemonpager.data.local.PokemonEntity
import dev.thunderbolt.pokemonpager.data.mapper.toPokemon
import dev.thunderbolt.pokemonpager.data.mapper.toPokemonEntity
import dev.thunderbolt.pokemonpager.data.remote.PokemonApi
import dev.thunderbolt.pokemonpager.domain.entity.Pokemon
import dev.thunderbolt.pokemonpager.domain.entity.Response
import dev.thunderbolt.pokemonpager.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokemonPager: Pager<Int, PokemonEntity>,
    private val pokemonDatabase: PokemonDatabase,
    private val pokemonApi: PokemonApi,
) : PokemonRepository {

    override fun getPokemons(): Flow<PagingData<Pokemon>> {
        return pokemonPager.flow.map { pagingData ->
            pagingData.map { it.toPokemon() }
        }
    }

    override fun getPokemon(id: Int): Flow<Response<Pokemon>> = flow {
        val localData = pokemonDatabase.dao.getById(id)
        if (localData != null) emit(Response.Loading(localData))
        try {
            val remoteData = pokemonApi.getPokemon(id)!!.toPokemon()
            pokemonDatabase.dao.insert(remoteData.toPokemonEntity())
            emit(Response.Success(remoteData))
        } catch (e: ApolloException) {
            emit(Response.Error(error = e.message.toString(), data = localData))
        }
    }
}
