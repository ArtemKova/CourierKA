package com.example.data.domain

import com.example.data.data.Order

class GetOrderItemUseCase (private val orderListRepository: OrderListRepository){

    suspend fun getOrderItem(order:Order){
        orderListRepository.getOrderItem(order)
    }
}