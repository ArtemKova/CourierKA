package com.ka.courierka.domain

import com.ka.courierka.order.Order

class EditOrderItemUseCase (private val orderListRepository: OrderListRepository){
    suspend fun editOrderItem(order: Order){
        orderListRepository.editOrderItem(order)
    }
}