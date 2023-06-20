package dev.thunderbolt.pokemonpager.ui.pokemon.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun PokemonDetailPage() {
    var counter by rememberSaveable { mutableStateOf(0) }

    Box(modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = {
                counter++
            },
            modifier = Modifier.align(Alignment.Center),
        ) {
            Text(
                "Increase -> $counter",
            )
        }
    }
}
