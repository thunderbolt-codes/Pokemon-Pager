package dev.thunderbolt.pokemonpager.ui.pokemon.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.thunderbolt.pokemonpager.domain.entity.Pokemon
import dev.thunderbolt.pokemonpager.domain.entity.Response


@Composable
fun PokemonDetailPage(showSnackbar: suspend (String) -> Unit) {
    val viewModel = hiltViewModel<PokemonDetailViewModel>()
    val pokemonResponse = viewModel.pokemonResponse.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = pokemonResponse.value) {
        if (pokemonResponse.value is Response.Error) {
            showSnackbar(pokemonResponse.value.error!!)
        }
    }

    PokemonDetailContent(pokemonResponse = pokemonResponse.value)
}

@Composable
fun PokemonDetailContent(pokemonResponse: Response<Pokemon>) {
    Box(modifier = Modifier.fillMaxSize()) {
        when (pokemonResponse) {
            is Response.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            else -> {
                if (pokemonResponse.data != null) {
                    Text(
                        pokemonResponse.data.name,
                        modifier = Modifier.align(Alignment.Center),
                    )
                }
            }
        }
    }
}
