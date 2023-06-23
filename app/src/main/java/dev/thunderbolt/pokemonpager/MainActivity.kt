package dev.thunderbolt.pokemonpager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.get
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import dev.thunderbolt.pokemonpager.ui.custom.TopBar
import dev.thunderbolt.pokemonpager.ui.pokemon.detail.PokemonDetailPage
import dev.thunderbolt.pokemonpager.ui.pokemon.list.PokemonListPage
import dev.thunderbolt.pokemonpager.ui.theme.PokemonPagerTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokemonPagerTheme {
                val navController = rememberNavController()
                val snackbarHostState = remember { SnackbarHostState() }

                Scaffold(
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                    topBar = { TopBar(navController = navController) },
                ) { padding ->
                    NavHost(
                        modifier = Modifier.padding(padding),
                        navController = navController,
                        startDestination = "pokemons",
                    ) {
                        composable(
                            "pokemons",
                            label = "Gotta Catch 'Em All!",
                        ) {
                            PokemonListPage(
                                navigateToDetail = { id ->
                                    navController.navigate("pokemon/$id")
                                },
                                showSnackbar = snackbarHostState::showSnackbar,
                            )
                        }
                        composable(
                            "pokemon/{id}",
                            label = "Pokemon",
                            arguments = listOf(navArgument("id") { type = NavType.IntType }),
                        ) { backStackEntry ->
                            PokemonDetailPage(backStackEntry.arguments!!.getInt("id"))
                        }
                    }
                }
            }
        }
    }
}

fun NavGraphBuilder.composable(
    route: String,
    label: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable (NavBackStackEntry) -> Unit
) {
    addDestination(ComposeNavigator.Destination(provider[ComposeNavigator::class], content).apply {
        this.route = route
        this.label = label // SET LABEL
        arguments.forEach { (argumentName, argument) ->
            addArgument(argumentName, argument)
        }
        deepLinks.forEach { deepLink ->
            addDeepLink(deepLink)
        }
    })
}
