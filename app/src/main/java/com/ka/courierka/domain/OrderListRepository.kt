package com.ka.courierka.domain

import androidx.lifecycle.LiveData
import com.ka.courierka.order.Order

interface OrderListRepository {
    suspend fun addOrderItem(order:Order)
    suspend fun deleteOrderItem(order:Order)
    suspend fun editOrderItem(order:Order)
    suspend fun getOrderItem(order:Order)
    fun getOrderList(): LiveData<List<Order>>

}