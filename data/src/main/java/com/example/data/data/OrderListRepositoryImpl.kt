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
class OrderListRepositoryImpl @Inject constructor(
    private val orderListDao: OrderListDao
) {


    private val mapper = OrderListMapper()
     suspend fun addOrderItem(order: Order) {
        orderListDao.addOrderItem(mapper.mapOrderToDBOrder(order))
    }

     suspend fun deleteOrderItem(order: Order) {
        orderListDao.deleteOrderItem(order.id)
    }

     suspend fun editOrderItem(order: Order) {
        orderListDao.addOrderItem(mapper.mapOrderToDBOrder(order))
    }

     suspend fun getOrderItem(orderId: String): Order {
        return orderListDao.getOrderItem(orderId)
    }
     fun getOrderList(): LiveData<List<Order>> = orderListDao.getOrderList().map {
        mapper.mapListDBOrderToListOrder(it)
    }

    suspend fun clear(){
        orderListDao.delete()
    }

}