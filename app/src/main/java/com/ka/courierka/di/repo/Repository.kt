package com.ka.courierka.di.repo

import com.ka.courierka.di.TypeOrder


interface Repository {
    suspend fun doNetworkCall(): Resource<List<TypeOrder>>
}