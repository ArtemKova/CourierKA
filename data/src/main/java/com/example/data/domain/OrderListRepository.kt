package com.example.data.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.data.data.Order

interface OrderListRepository {
    suspend fun addOrderItem(order:Order)
    suspend fun deleteOrderItem(order:Order)
    suspend fun editOrderItem(order:Order)
    suspend fun getOrderItem(order:Order)
     fun getOrderList(): LiveData<List<Order>>

}