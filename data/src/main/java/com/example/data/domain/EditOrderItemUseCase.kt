package com.example.data.domain

import com.example.data.data.Order

class EditOrderItemUseCase (private val orderListRepository: OrderListRepository){
    suspend fun editOrderItem(order: Order){
        orderListRepository.editOrderItem(order)
    }
}