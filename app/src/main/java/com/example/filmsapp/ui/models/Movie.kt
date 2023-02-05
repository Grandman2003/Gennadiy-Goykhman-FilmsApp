package com.example.filmsapp.ui.models

import android.graphics.Bitmap

data class Movie(
    val id: Int,
    val title: String,
    var description: String = "",
    val image: Bitmap,
    val genres: List<String>,
    val countries: List<String>,
    val year: Int,
    val isFavourite: Boolean = false
)
