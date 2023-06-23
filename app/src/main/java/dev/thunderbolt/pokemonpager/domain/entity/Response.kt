package dev.thunderbolt.pokemonpager.domain.entity

sealed class Response<T>(
    val data: T? = null,
    val error: String? = null,
) {
    class Loading<T> : Response<T>()
    data class Success<T>(val d: T) : Response<T>(data = d)
    data class Error<T>(val e: String, val d: T? = null) : Response<T>(error = e, data = d)
}
