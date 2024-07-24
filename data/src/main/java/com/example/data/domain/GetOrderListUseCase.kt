package com.example.data.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.data.data.Order

class GetOrderListUseCase (private val orderListRepository: OrderListRepository){

     fun getOrderList(): LiveData<List<Order>> {
        return orderListRepository.getOrderList()
    }
}