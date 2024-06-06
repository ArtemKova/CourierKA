package com.ka.courierka.coin.repo

import com.ka.courierka.coin.Apifortype
import com.ka.courierka.coin.Typeorder

class RepositoryImpl(private val api: Apifortype) : Repository{
    override suspend fun doNetworkCall(): Resource<List<Typeorder>> {
        return try {
            val response = api.doNetworkCall()
            if(response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error", null)
            }else{
                Resource.error("Error", null)
            }
        }catch (e: Exception){
            Resource.error("No Data!", null)
        }
    }
}