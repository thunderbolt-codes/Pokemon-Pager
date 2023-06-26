package dev.thunderbolt.pokemonpager.ui.pokemon.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import dev.thunderbolt.pokemonpager.domain.entity.Pokemon
import dev.thunderbolt.pokemonpager.domain.entity.Response

@Composable
fun PokemonDetailScreen(snackbarHostState: SnackbarHostState) {
    val viewModel = hiltViewModel<PokemonDetailViewModel>()
    val pokemonResponse by viewModel.pokemonResponse.collectAsStateWithLifecycle()

    if (pokemonResponse is Response.Error) {
        LaunchedEffect(key1 = snackbarHostState) {
            snackbarHostState.showSnackbar((pokemonResponse as Response.Error).error)
        }
    }

    PokemonDetailContent(pokemonResponse = pokemonResponse)
}

@Composable
fun PokemonDetailContent(pokemonResponse: Response<Pokemon>) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (pokemonResponse.data != null) {
            val pokemon = pokemonResponse.data!!

            Row(modifier = Modifier.padding(20.dp)) {
                AsyncImage(
                    model = pokemon.imageUrl,
                    modifier = Modifier.width(100.dp),
                    contentScale = ContentScale.Fit,
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(20.dp))
                Column {
                    Text(
                        pokemon.name,
                        style = MaterialTheme.typography.titleLarge,
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        pokemon.types.mapIndexed { index, type ->
                            Text(type)
                            if (index < pokemon.types.lastIndex) Text("•")
                        }
                    }
                }
            }
        }
        if (pokemonResponse is Response.Loading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
    }
}
