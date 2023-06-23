package dev.thunderbolt.pokemonpager.domain.repository

import androidx.paging.PagingData
import dev.thunderbolt.pokemonpager.domain.entity.Pokemon
import dev.thunderbolt.pokemonpager.domain.entity.Response
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {
    fun getPokemons(): Flow<PagingData<Pokemon>>
    fun getPokemon(id: Int): Flow<Response<Pokemon>>
}
