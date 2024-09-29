package com.example.data.domain

import com.example.data.data.Order
import javax.inject.Inject


class GetOrderItemUseCase @Inject constructor(private val orderListRepository: OrderListRepository){
     suspend fun getOrderItem(orderId:String):Order{
         return orderListRepository.getOrderItem(orderId)
    }
}