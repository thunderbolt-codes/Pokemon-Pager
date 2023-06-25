package dev.thunderbolt.pokemonpager.ui.pokemon.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import dev.thunderbolt.pokemonpager.domain.entity.Pokemon

@Composable
fun PokemonListPage(
    navigateToDetail: (Int) -> Unit,
    showSnackbar: suspend (String) -> Unit,
) {
    val viewModel = hiltViewModel<PokemonListViewModel>()
    val pokemonPagingItems = viewModel.pokemonPagingDataFlow.collectAsLazyPagingItems()

    LaunchedEffect(key1 = pokemonPagingItems.loadState) {
        if (pokemonPagingItems.loadState.refresh is LoadState.Error) {
            showSnackbar(
                (pokemonPagingItems.loadState.refresh as LoadState.Error).error.message ?: ""
            )
        }
    }

    PokemonListContent(
        pokemonPagingItems = pokemonPagingItems,
        navigateToDetail = navigateToDetail,
    )
}

@Composable
fun PokemonListContent(
    pokemonPagingItems: LazyPagingItems<Pokemon>,
    navigateToDetail: (Int) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (pokemonPagingItems.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                items(
                    count = pokemonPagingItems.itemCount,
                    key = pokemonPagingItems.itemKey { it.id },
                ) { index ->
                    val pokemon = pokemonPagingItems[index]
                    if (pokemon != null) {
                        PokemonItem(
                            pokemon,
                            onClick = {
                                navigateToDetail(pokemon.id)
                            },
                            modifier = Modifier.fillMaxWidth(),
                        )
                        Divider(
                            color = MaterialTheme.colorScheme.secondary,
                            thickness = 0.5.dp,
                            modifier = Modifier.padding(horizontal = 20.dp),
                        )
                    }
                }
                item {
                    if (pokemonPagingItems.loadState.append is LoadState.Loading) {
                        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                    }
                }
            }
        }
    }
}
