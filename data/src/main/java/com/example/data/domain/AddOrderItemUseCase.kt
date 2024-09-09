package com.example.data.domain

import com.example.data.data.Order
import com.example.data.data.OrderListRepositoryImpl

class AddOrderItemUseCase (private val orderListRepository: OrderListRepositoryImpl) {
    suspend fun addOrderItem(order: Order){
        orderListRepository.addOrderItem(order)
    }
}