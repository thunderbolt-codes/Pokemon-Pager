package dev.thunderbolt.pokemonpager.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.thunderbolt.pokemonpager.domain.entity.Pokemon

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<PokemonEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: PokemonEntity)

    @Query("SELECT * FROM pokemonentity WHERE id=:id")
    suspend fun getById(id: Int): Pokemon?

    @Query("SELECT * FROM pokemonentity")
    fun pagingSource(): PagingSource<Int, PokemonEntity>

    @Query("DELETE FROM pokemonentity")
    suspend fun clearAll()
}
