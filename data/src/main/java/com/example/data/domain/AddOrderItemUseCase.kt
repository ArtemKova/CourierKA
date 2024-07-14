package com.example.data.domain

import com.example.data.data.Order

class AddOrderItemUseCase (private val orderListRepository: OrderListRepository) {
    suspend fun addOrderItem(order: Order){
        orderListRepository.addOrderItem(order)
    }
}