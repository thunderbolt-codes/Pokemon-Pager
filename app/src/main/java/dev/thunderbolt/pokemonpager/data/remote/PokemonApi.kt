package dev.thunderbolt.pokemonpager.data.remote

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import com.apollographql.apollo3.network.http.LoggingInterceptor
import dev.thunderbolt.pokemonpager.data.PokemonDetailQuery
import dev.thunderbolt.pokemonpager.data.PokemonListQuery
import javax.inject.Inject

class PokemonApi @Inject constructor() {

    private val BASE_URL = "https://graphql-pokeapi.graphcdn.app/graphql"

    private val apolloClient =
        ApolloClient.Builder().serverUrl(BASE_URL).addHttpInterceptor(LoggingInterceptor()).build()

    suspend fun getPokemons(offset: Int, limit: Int): PokemonListQuery.Pokemons? {
        val response = apolloClient.query(
            PokemonListQuery(
                offset = offset,
                limit = limit,
            )
        ).execute()
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
