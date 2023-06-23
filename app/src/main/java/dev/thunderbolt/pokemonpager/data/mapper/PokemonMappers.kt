package dev.thunderbolt.pokemonpager.data.mapper

import dev.thunderbolt.pokemonpager.data.PokemonDetailQuery
import dev.thunderbolt.pokemonpager.data.PokemonListQuery
import dev.thunderbolt.pokemonpager.data.local.PokemonEntity
import dev.thunderbolt.pokemonpager.domain.entity.Pokemon
import java.util.Locale

fun PokemonEntity.toPokemon() = Pokemon(
    id = id,
    name = name,
    imageUrl = imageUrl,
    types = types,
)

fun Pokemon.toPokemonEntity() = PokemonEntity(
    id = id,
    name = name,
    imageUrl = imageUrl,
    types = types,
)

fun PokemonListQuery.Result.toPokemonEntity() = PokemonEntity(
    id = id ?: 0,
    name = name?.replaceFirstChar { it.uppercase(Locale.US) } ?: "",
    imageUrl = image ?: "",
    types = emptyList(),
)

fun PokemonDetailQuery.Pokemon.toPokemon() = Pokemon(
    id = id ?: 0,
    name = name?.replaceFirstChar { it.uppercase(Locale.US) } ?: "",
    imageUrl = sprites?.front_default ?: "",
    types = types?.mapNotNull { it?.type?.name } ?: emptyList(),
)
