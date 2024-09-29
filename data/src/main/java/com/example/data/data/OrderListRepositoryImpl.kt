package com.example.data.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.example.data.domain.OrderListRepository


import androidx.lifecycle.map
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class OrderListRepositoryImpl @Inject constructor(
    private val orderListDao: OrderListDao
):OrderListRepository {

    private val mapper = OrderListMapper()
     override suspend fun addOrderItem(order: Order) {
        orderListDao.addOrderItem(mapper.mapOrderToDBOrder(order))
    }

     override suspend fun deleteOrderItem(order: Order) {
        orderListDao.deleteOrderItem(order.id)
    }

     override suspend fun editOrderItem(order: Order) {
        orderListDao.addOrderItem(mapper.mapOrderToDBOrder(order))
    }

     override suspend fun getOrderItem(orderId: String): Order {
        return orderListDao.getOrderItem(orderId)
    }
     override fun getOrderList(): LiveData<List<Order>> = orderListDao.getOrderList().map {
        mapper.mapListDBOrderToListOrder(it)
    }



}