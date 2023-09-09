package dev.thunderbolt.pokemonpager.presentation.pokemon.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.thunderbolt.pokemonpager.domain.entity.Pokemon
import dev.thunderbolt.pokemonpager.presentation.theme.PokemonPagerTheme

@Composable
fun PokemonItem(
    pokemon: Pokemon,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = pokemon.imageUrl,
            modifier = Modifier.size(60.dp),
            contentScale = ContentScale.Fit,
            contentDescription = null,
        )
        Text(
            "#${pokemon.id} - ${pokemon.name}",
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
        )
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            tint = MaterialTheme.colorScheme.secondary,
            contentDescription = null,
        )
    }
}

@Preview
@Composable
fun PokemonItemPreview() {
    PokemonPagerTheme {
        Surface {
            PokemonItem(
                pokemon = Pokemon(
                    id = 1,
                    name = "Bulbasaur",
                    imageUrl = "",
                ),
                onClick = {},
            )
        }
    }
}
