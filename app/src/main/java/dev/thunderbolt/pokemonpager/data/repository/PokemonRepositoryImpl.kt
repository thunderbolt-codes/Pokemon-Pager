package dev.thunderbolt.pokemonpager.data.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import dev.thunderbolt.pokemonpager.data.local.PokemonEntity
import dev.thunderbolt.pokemonpager.data.mapper.toPokemon
import dev.thunderbolt.pokemonpager.domain.entity.Pokemon
import dev.thunderbolt.pokemonpager.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokemonPager: Pager<Int, PokemonEntity>
) : PokemonRepository {

    override fun getPokemons(): Flow<PagingData<Pokemon>> {
        return pokemonPager.flow.map { pagingData ->
                pagingData.map { it.toPokemon() }
            }
    }
}
