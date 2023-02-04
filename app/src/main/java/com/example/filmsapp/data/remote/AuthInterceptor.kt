package com.example.filmsapp.data.remote

import com.example.filmsapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation

class AuthInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val invocation: Invocation = chain.request().tag(Invocation::class.java)
            ?: return chain.proceed(chain.request())

        val shouldAttachHeader = invocation.method()
            .annotations
            .any { it.annotationClass == AuthToken::class }

        return if(shouldAttachHeader){
            chain.proceed(
                chain.request()
                    .newBuilder()
                    .addHeader("X-API-KEY", BuildConfig.KINOPOISK_API_KEY)
                    .build()
            )
        } else {
            chain.proceed(chain.request())
        }
    }
}