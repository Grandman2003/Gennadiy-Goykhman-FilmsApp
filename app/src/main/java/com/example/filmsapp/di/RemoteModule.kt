package com.example.filmsapp.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.filmsapp.data.remote.AuthInterceptor
import com.example.filmsapp.data.remote.MovieApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {
    private val baseURL = "https://kinopoiskapiunofficial.tech/"

    @Provides
    fun provideClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

    @Provides
    fun provideMovieApi(client: OkHttpClient): MovieApi =
        Retrofit.Builder()
            .baseUrl(baseURL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create().asLenient())
            .build()
            .create(MovieApi::class.java)

    @Provides
    fun provideGlideRequestManager(@ApplicationContext context: Context): RequestManager = Glide.with(context)
}