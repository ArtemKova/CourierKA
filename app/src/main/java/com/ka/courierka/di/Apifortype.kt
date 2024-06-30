package com.ka.courierka.di

import retrofit2.Response
import retrofit2.http.GET

interface Apifortype {
    @GET("posts")
    suspend fun doNetworkCall(): Response<List<Typeorder>>
}