package com.example.filmsapp.ui.models.samples

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.example.filmsapp.R
import com.example.filmsapp.ui.models.Movie

object SampleUiData {
    fun getMovieCard(context: Context): Movie =
        Movie(
            id = 1,
            title = "Снегурочка",
            genres = listOf("Боевик", "Стратегия"),
            year = 1786,
            image = ContextCompat.getDrawable(context, R.drawable.film_main_picture)!!.toBitmap(),
            isFavourite = true,
            description = "Сопротивление собирает отряд для выполнения особой миссии - надо выкрасть чертежи самого совершенного и мертоносного оружия Империи. Не всем суждено вернуться домой, но герои готовы к этому, ведь на кону судьба Галактики",
            countries = listOf("Россия")
        )
}