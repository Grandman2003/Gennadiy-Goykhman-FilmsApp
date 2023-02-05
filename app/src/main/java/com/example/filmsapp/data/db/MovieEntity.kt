package com.example.filmsapp.data.db

import androidx.annotation.NonNull
import androidx.navigation.navArgument
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey val Id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "year") val year: Int,
    @ColumnInfo(name = "poster") val poster: ByteArray,
    @ColumnInfo(name = "description") val description: String? = "",
    @ColumnInfo(name = "countries") val countries: List<String>,
    @ColumnInfo(name = "genres") val genres: List<String>,
    @ColumnInfo(name = "favourite") var isFavourite: Int = 0
)