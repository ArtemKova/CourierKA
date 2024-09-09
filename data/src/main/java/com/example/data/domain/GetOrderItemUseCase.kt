package com.example.data.domain

import com.example.data.data.Order
import com.example.data.data.OrderListRepositoryImpl


class GetOrderItemUseCase (private val orderListRepository: OrderListRepositoryImpl){
     suspend fun getOrderItem(orderId:String):Order{
         return orderListRepository.getOrderItem(orderId)
    }
}