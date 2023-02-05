package com.example.filmsapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovie(vararg movie: MovieEntity)

    @Query("SELECT * FROM movie")
    fun getAllMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movie WHERE Id=:id")
    suspend fun getMovieById(id: Int): MovieEntity

    @Query("DELETE FROM movie")
    suspend fun deleteTable()

    @Update
    suspend fun updateMovie(entity: MovieEntity)
}