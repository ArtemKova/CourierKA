package com.ka.courierka.domain

import com.ka.courierka.order.Order

class GetOrderItemUseCase (private val orderListRepository: OrderListRepository){

    suspend fun getOrderItem(order:Order){
        orderListRepository.getOrderItem(order)
    }
}