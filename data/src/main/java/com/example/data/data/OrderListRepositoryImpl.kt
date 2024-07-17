package com.example.data.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.example.data.domain.OrderListRepository


import androidx.lifecycle.map

class OrderListRepositoryImpl(application: Application): OrderListRepository {

    private val orderListDao = AppDataBase.getInstance(application).orderListDao()
    private val mapper = OrderListMapper()
//    val orderList = orderListDao.getOrderList()

    override suspend fun addOrderItem(order: Order) {
        orderListDao.addOrderItem(mapper.mapOrderToDBOrder(order))
    }

    override suspend fun deleteOrderItem(order: Order) {
        orderListDao.deleteOrderItem(order.id)
    }

    override suspend fun editOrderItem(order: Order) {
        orderListDao.addOrderItem(mapper.mapOrderToDBOrder(order))
    }

    override suspend fun getOrderItem(order: Order) {
        orderListDao.addOrderItem(mapper.mapOrderToDBOrder(order))
    }

    override fun getOrderList(): LiveData<List<Order>> = orderListDao.getOrderList().map {
        mapper.mapListDBOrderToListOrder(it)
    }

    suspend fun clear(){
        orderListDao.delete()
    }

}