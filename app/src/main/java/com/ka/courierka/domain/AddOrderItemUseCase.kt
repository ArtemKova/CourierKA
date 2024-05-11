package com.ka.courierka.domain

import com.ka.courierka.order.Order

class AddOrderItemUseCase (private val orderListRepository: OrderListRepository) {
    suspend fun addOrderItem(order: Order){
        orderListRepository.addOrderItem(order)
    }
}