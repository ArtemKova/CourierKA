package com.example.data.domain

import com.example.data.data.Order
import javax.inject.Inject

class DeleteOrderItemUseCase @Inject constructor(private val orderListRepository: OrderListRepository){
    suspend fun deleteOrderItem(order: Order){
        orderListRepository.deleteOrderItem(order)
    }
}