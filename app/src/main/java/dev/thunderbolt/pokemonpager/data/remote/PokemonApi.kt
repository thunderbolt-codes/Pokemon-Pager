package dev.thunderbolt.pokemonpager.data.remote

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.network.http.LoggingInterceptor
import dev.thunderbolt.pokemonpager.data.PokemonDetailQuery
import dev.thunderbolt.pokemonpager.data.PokemonListQuery
import java.io.IOException

class PokemonApi {

    private val BASE_URL = "https://graphql-pokeapi.graphcdn.app/graphql"

    private val apolloClient = ApolloClient.Builder()
        .serverUrl(BASE_URL)
        .addHttpInterceptor(LoggingInterceptor())
        .build()

    suspend fun getPokemonList(offset: Int, limit: Int): PokemonListQuery.Pokemons? {
        val response = apolloClient.query(
            PokemonListQuery(
                offset = offset,
                limit = limit,
            )
        ).execute()

        if (response.data != null) {
            return response.data!!.pokemons
        } else {
            // ERROR OCCURRED
            if (response.exception != null) { // FETCH ERRORS
                throw IOException(response.exception.toString())
            } else { // GRAPHQL ERRORS
                throw IOException(response.errors.toString())
            }
        }
    }

    suspend fun getPokemon(id: Int): PokemonDetailQuery.Pokemon? {
        val response = apolloClient.query(
            PokemonDetailQuery(id = id.toString())
        ).execute()

        if (response.data != null) {
            return response.data!!.pokemon
        } else {
            // Something wrong happened
            if (response.exception != null) {
                throw IOException(response.exception.toString())
            } else {
                throw IOException(response.errors.toString())
            }
        }
    }
}
