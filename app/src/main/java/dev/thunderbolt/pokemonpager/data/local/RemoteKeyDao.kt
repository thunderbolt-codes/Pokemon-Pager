package dev.thunderbolt.pokemonpager.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: RemoteKeyEntity)

    @Query("SELECT * FROM remote_key WHERE id = :id")
    suspend fun getById(id: String): RemoteKeyEntity?

    @Query("DELETE FROM remote_key WHERE id = :id")
    suspend fun deleteById(id: String)
}
