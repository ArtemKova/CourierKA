package com.example.data.domain

import androidx.lifecycle.LiveData
import com.example.data.data.Order
import dagger.Provides

interface OrderListRepository {

    suspend fun addOrderItem(order:Order)

    suspend fun deleteOrderItem(order:Order)

    suspend fun editOrderItem(order:Order)

    suspend fun getOrderItem(orderid: String):Order

    fun getOrderList(): LiveData<List<Order>>
}