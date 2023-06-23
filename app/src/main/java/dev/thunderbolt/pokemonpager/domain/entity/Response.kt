package dev.thunderbolt.pokemonpager.domain.entity

sealed class Response<T> {

    abstract val data: T?

    data class Loading<T>(override val data: T? = null) : Response<T>()
    data class Success<T>(override val data: T) : Response<T>()
    data class Error<T>(val error: String, override val data: T? = null) : Response<T>()
}
