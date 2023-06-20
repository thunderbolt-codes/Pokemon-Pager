package dev.thunderbolt.pokemonpager.ui.pokemon.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun PokemonListPage(
    navigateToDetail: (Int) -> Unit,
) {
    val viewModel = hiltViewModel<PokemonListViewModel>()

    Box(modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = {
                viewModel.increaseCounter()
                navigateToDetail(123)
            },
            modifier = Modifier.align(Alignment.Center),
        ) {
            Text(
                "Go! + ${viewModel.counter}",
            )
        }
    }
}
