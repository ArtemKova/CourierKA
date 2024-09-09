package com.example.data.domain

import com.example.data.data.Order
import com.example.data.data.OrderListRepositoryImpl

class EditOrderItemUseCase (private val orderListRepository: OrderListRepositoryImpl){
    suspend fun editOrderItem(order: Order){
        orderListRepository.editOrderItem(order)
    }
}