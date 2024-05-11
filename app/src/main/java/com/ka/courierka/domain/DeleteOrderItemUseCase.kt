package com.ka.courierka.domain

import com.ka.courierka.order.Order

class DeleteOrderItemUseCase (private val orderListRepository: OrderListRepository){
    suspend fun deleteOrderItem(order: Order){
        orderListRepository.deleteOrderItem(order)
    }
}