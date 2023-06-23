package dev.thunderbolt.pokemonpager.data.mapper

import dev.thunderbolt.pokemonpager.data.PokemonListQuery
import dev.thunderbolt.pokemonpager.data.local.PokemonEntity
import dev.thunderbolt.pokemonpager.domain.entity.Pokemon
import java.util.Locale

fun PokemonEntity.toPokemon() = Pokemon(
    id = id,
    name = name,
    imageUrl = imageUrl,
)

fun PokemonListQuery.Result.toPokemonEntity() = PokemonEntity(
    id = id ?: 0,
    name = name?.replaceFirstChar { it.uppercase(Locale.US) } ?: "",
    imageUrl = image ?: "",
)
