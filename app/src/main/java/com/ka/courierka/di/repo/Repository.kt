package com.ka.courierka.di.repo

import com.ka.courierka.di.Typeorder

interface Repository {
    suspend fun doNetworkCall(): Resource<List<Typeorder>>
}