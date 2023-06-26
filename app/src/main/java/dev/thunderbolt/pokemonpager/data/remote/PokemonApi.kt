package dev.thunderbolt.pokemonpager.data.remote

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import com.apollographql.apollo3.network.http.LoggingInterceptor
import dev.thunderbolt.pokemonpager.data.PokemonDetailQuery
import dev.thunderbolt.pokemonpager.data.PokemonListQuery

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
        // IF RESPONSE HAS ERRORS OR DATA IS NULL, THROW EXCEPTION
        if (response.hasErrors() || response.data == null) {
            throw ApolloException(response.errors.toString())
        }
        return response.data!!.pokemons
    }

    suspend fun getPokemon(id: Int): PokemonDetailQuery.Pokemon? {
        val response = apolloClient.query(
            PokemonDetailQuery(id = id.toString())
        ).execute()
        if (response.hasErrors() || response.data == null) {
            throw ApolloException(response.errors.toString())
        }
        return response.data!!.pokemon
    }
}
