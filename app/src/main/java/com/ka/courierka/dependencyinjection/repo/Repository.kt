package com.ka.courierka.dependencyinjection.repo

import com.ka.courierka.dependencyinjection.TypeOrder

import javax.inject.Inject


interface Repository {
    suspend fun doNetworkCall(): Resource<List<TypeOrder>>
}