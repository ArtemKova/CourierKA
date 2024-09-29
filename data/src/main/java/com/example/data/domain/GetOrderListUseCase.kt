package com.example.data.domain

import androidx.lifecycle.LiveData
import com.example.data.data.Order
import javax.inject.Inject

class GetOrderListUseCase @Inject constructor(private val orderListRepository: OrderListRepository){
     fun getOrderList(): LiveData<List<Order>> {
        return orderListRepository.getOrderList()
    }
}