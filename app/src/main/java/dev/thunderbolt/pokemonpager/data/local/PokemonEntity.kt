package dev.thunderbolt.pokemonpager.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("pokemon")
data class PokemonEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val imageUrl: String,
    val types: List<String>,
)
