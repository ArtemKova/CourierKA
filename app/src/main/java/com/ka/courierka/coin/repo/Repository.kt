package com.ka.courierka.coin.repo

import com.ka.courierka.coin.Typeorder

interface Repository {
    suspend fun doNetworkCall(): Resource<List<Typeorder>>
}