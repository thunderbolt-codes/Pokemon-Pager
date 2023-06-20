package dev.thunderbolt.pokemonpager.ui.pokemon.list

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.saveable
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    var counter by savedStateHandle.saveable {
        mutableStateOf(0)
    }

    fun increaseCounter() {
        counter++
    }
}
