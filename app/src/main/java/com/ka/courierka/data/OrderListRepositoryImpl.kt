package com.ka.courierka.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.ka.courierka.domain.OrderListRepository
import com.ka.courierka.order.Order

import androidx.lifecycle.map

class OrderListRepositoryImpl(application: Application):OrderListRepository {

    private val orderListDao = AppDataBase.getInstance(application).orderListDao()
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

    override suspend fun getOrderItem(order: Order) {
//        orderListDao.addOrderItem(mapper.mapOrderToDBOrder(value))
    }

    override fun getOrderList(): LiveData<List<Order>> = orderListDao.getOrderList().map {
        mapper.mapListDBOrderToListOrder(it)
    }
//        MediatorLiveData<List<Order>>().apply {
//        addSource(orderListDao.getOrderList()){
//            value = mapper.mapListDBOrderToListOrder(it)



}