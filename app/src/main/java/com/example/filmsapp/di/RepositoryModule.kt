package com.example.filmsapp.di

import com.example.filmsapp.data.repository.MovieRepository
import com.example.filmsapp.data.repository.MovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun MovieRepositoryImpl.provideMovieRepo(): MovieRepository
}