package dev.thunderbolt.pokemonpager.presentation.pokemon.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.thunderbolt.pokemonpager.domain.entity.Pokemon
import dev.thunderbolt.pokemonpager.domain.usecase.GetPokemonList
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val getPokemonList: GetPokemonList,
) : ViewModel() {

    val pokemonPagingDataFlow: Flow<PagingData<Pokemon>> = getPokemonList()
        .cachedIn(viewModelScope)
}
