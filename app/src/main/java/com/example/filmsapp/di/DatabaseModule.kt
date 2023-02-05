package com.example.filmsapp.di

import android.content.Context
import androidx.room.Room
import com.example.filmsapp.data.db.MovieDao
import com.example.filmsapp.data.db.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun getMovieDao(@ApplicationContext context: Context): MovieDao =
        Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            "movies_db"
        )
            .build()
            .movieDao()
}