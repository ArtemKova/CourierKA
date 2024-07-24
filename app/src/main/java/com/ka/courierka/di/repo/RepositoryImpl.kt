package com.ka.courierka.di.repo

import com.ka.courierka.di.TypeOrder
import com.ka.courierka.di.Apifortype
import javax.inject.Inject

class RepositoryImpl @Inject constructor( private val api: Apifortype) : Repository {


    override suspend fun doNetworkCall(): Resource<List<TypeOrder>> {
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