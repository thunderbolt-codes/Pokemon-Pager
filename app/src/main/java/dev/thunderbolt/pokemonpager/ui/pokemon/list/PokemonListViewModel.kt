package dev.thunderbolt.pokemonpager.ui.pokemon.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.thunderbolt.pokemonpager.domain.usecase.GetPokemons
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val getPokemons: GetPokemons,
) : ViewModel() {

    val pokemonPagingDataFlow = getPokemons().cachedIn(viewModelScope)

}
