package com.ka.courierka.di

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface Apifortype {
    @GET("posts")
    suspend fun doNetworkCall(): Response<List<TypeOrder>>

    companion object {
        fun create(): Apifortype {
            return Retrofit.Builder()
                .baseUrl("https://my-json-server.typicode.com/ArtemKova/fjsonforme/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Apifortype::class.java)
        }
    }
}