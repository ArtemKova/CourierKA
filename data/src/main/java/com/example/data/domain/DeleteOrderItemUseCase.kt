package com.example.data.domain

import com.example.data.data.Order

class DeleteOrderItemUseCase (private val orderListRepository: OrderListRepository){
    suspend fun deleteOrderItem(order: Order){
        orderListRepository.deleteOrderItem(order)
    }
}