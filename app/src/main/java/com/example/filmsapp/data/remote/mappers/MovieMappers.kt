package com.example.filmsapp.data.remote.mappers

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import androidx.core.graphics.drawable.toDrawable
import com.bumptech.glide.RequestManager
import com.example.filmsapp.data.db.MovieEntity
import com.example.filmsapp.data.remote.dto.MovieDto
import com.example.filmsapp.ui.models.Movie
import okhttp3.internal.notify
import java.io.ByteArrayOutputStream

object MovieMappers {
    fun MovieDto.toMovieEntity(requestManager: RequestManager) =
        MovieEntity(
            Id = this.filmId,
            title = this.name,
            year = this.year,
            poster = requestManager
                .load(this.url)
                .submit()
                .get()
                .run {
                    val bitmap = (this as BitmapDrawable).bitmap
                    val stream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                    stream.toByteArray()
                },
            countries = this.countries.map { it.country },
            description = this.description,
            genres = this.genres.map { it.genre }
        )

    fun MovieEntity.toMovie() =
        Movie(
            id = this.Id,
            title = this.title,
            description = this.description ?: "",
            image = BitmapFactory.decodeByteArray(this.poster,0,this.poster.size),
            genres = this.genres,
            countries = this.countries,
            year = year,
            isFavourite = this.isFavourite==1
        )
}