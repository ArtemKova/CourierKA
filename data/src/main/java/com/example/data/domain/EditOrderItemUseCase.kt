package com.example.data.domain

import com.example.data.data.Order
import javax.inject.Inject

class EditOrderItemUseCase @Inject constructor(private val orderListRepository: OrderListRepository){
    suspend fun editOrderItem(order: Order){
        orderListRepository.editOrderItem(order)
    }
}