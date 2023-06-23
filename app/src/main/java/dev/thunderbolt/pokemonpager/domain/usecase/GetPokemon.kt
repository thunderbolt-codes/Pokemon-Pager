package dev.thunderbolt.pokemonpager.domain.usecase

import dev.thunderbolt.pokemonpager.domain.entity.Pokemon
import dev.thunderbolt.pokemonpager.domain.entity.Response
import dev.thunderbolt.pokemonpager.domain.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetPokemon @Inject constructor(private val pokemonRepository: PokemonRepository) {
    operator fun invoke(id: Int): Flow<Response<Pokemon>> {
        return pokemonRepository.getPokemon(id).flowOn(Dispatchers.IO)
    }
}
