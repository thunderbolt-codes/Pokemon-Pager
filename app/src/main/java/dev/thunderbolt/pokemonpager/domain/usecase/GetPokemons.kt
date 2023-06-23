package dev.thunderbolt.pokemonpager.domain.usecase

import androidx.paging.PagingData
import dev.thunderbolt.pokemonpager.domain.entity.Pokemon
import dev.thunderbolt.pokemonpager.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokemons @Inject constructor(private val pokemonRepository: PokemonRepository) {
    operator fun invoke(): Flow<PagingData<Pokemon>> {
        return pokemonRepository.getPokemons()
    }
}
