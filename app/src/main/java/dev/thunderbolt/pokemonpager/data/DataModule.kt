package dev.thunderbolt.pokemonpager.data

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.thunderbolt.pokemonpager.data.local.PokemonDatabase
import dev.thunderbolt.pokemonpager.data.local.PokemonEntity
import dev.thunderbolt.pokemonpager.data.remote.PokemonApi
import dev.thunderbolt.pokemonpager.data.remote.PokemonRemoteMediator
import dev.thunderbolt.pokemonpager.data.repository.PokemonRepositoryImpl
import dev.thunderbolt.pokemonpager.domain.repository.PokemonRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun providePokemonDatabase(@ApplicationContext context: Context): PokemonDatabase {
        return Room.databaseBuilder(
            context,
            PokemonDatabase::class.java,
            "pokemon.db",
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun providePokemonApi(): PokemonApi {
        return PokemonApi()
    }

    @Provides
    @Singleton
    fun providePokemonPager(
        pokemonDatabase: PokemonDatabase,
        pokemonApi: PokemonApi,
    ): Pager<Int, PokemonEntity> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = PokemonRemoteMediator(
                pokemonDatabase = pokemonDatabase,
                pokemonApi = pokemonApi,
            ),
            pagingSourceFactory = {
                pokemonDatabase.pokemonDao.pagingSource()
            },
        )
    }

    @Provides
    @Singleton
    fun providePokemonRepository(
        pokemonPager: Pager<Int, PokemonEntity>,
        pokemonDatabase: PokemonDatabase,
        pokemonApi: PokemonApi,
    ): PokemonRepository {
        return PokemonRepositoryImpl(
            pokemonPager = pokemonPager,
            pokemonDatabase = pokemonDatabase,
            pokemonApi = pokemonApi,
        )
    }
}