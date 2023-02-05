package com.example.filmsapp.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.json.JSONObject

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun countriesToString(countries: List<String>): String =
        gson.toJson(Container(countries),Container::class.java)

    @TypeConverter
    fun fromStringToCountry(string: String): List<String> =
        gson.fromJson(string, Container::class.java).content

    @JvmInline
    value class Container(val content: List<String>)
}