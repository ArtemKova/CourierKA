package com.example.data.domain

import com.example.data.data.Order
import com.example.data.data.OrderListRepositoryImpl

class DeleteOrderItemUseCase (private val orderListRepository: OrderListRepositoryImpl){
    suspend fun deleteOrderItem(order: Order){
        orderListRepository.deleteOrderItem(order)
    }
}