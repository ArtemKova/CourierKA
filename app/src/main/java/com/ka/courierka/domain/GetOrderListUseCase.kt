package com.ka.courierka.domain

import androidx.lifecycle.LiveData
import com.ka.courierka.order.Order

class GetOrderListUseCase (private val orderListRepository: OrderListRepository){

    fun getOrderList(): LiveData<List<Order>>{
        return orderListRepository.getOrderList()
    }
}