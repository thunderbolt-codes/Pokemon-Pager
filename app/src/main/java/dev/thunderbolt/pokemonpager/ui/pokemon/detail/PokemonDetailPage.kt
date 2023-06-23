package dev.thunderbolt.pokemonpager.ui.pokemon.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun PokemonDetailPage(id: Int) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            "ID: $id",
            modifier = Modifier.align(Alignment.Center),
        )
    }
}
