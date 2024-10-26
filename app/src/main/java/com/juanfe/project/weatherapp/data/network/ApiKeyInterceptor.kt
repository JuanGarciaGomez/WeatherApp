package com.juanfe.project.weatherapp.data.network

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        // Add param apiKey for the URL
        val newUrl = originalUrl.newBuilder()
            .addQueryParameter("key", apiKey)
            .build()

        // Build a new request with a new url param
        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}
