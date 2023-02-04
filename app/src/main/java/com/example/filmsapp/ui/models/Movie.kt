package com.example.filmsapp.ui.models

import android.graphics.drawable.Drawable

data class Movie(
    val title: String,
    var description: String = "",
    val image: Drawable,
    val genres: List<String>,
    val countries: List<String>,
    val year: Int,
    val isFavourite: Boolean = false
)
