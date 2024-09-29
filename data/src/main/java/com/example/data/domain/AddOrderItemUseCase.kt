package com.example.data.domain

import com.example.data.data.Order
import javax.inject.Inject

class AddOrderItemUseCase @Inject constructor(private val orderListRepository: OrderListRepository) {
    suspend fun addOrderItem(order: Order){
        orderListRepository.addOrderItem(order)
    }
}