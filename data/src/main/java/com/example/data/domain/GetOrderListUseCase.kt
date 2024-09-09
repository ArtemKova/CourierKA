package com.example.data.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.data.data.Order
import com.example.data.data.OrderListRepositoryImpl

class GetOrderListUseCase (private val orderListRepository: OrderListRepositoryImpl){
     fun getOrderList(): LiveData<List<Order>> {
        return orderListRepository.getOrderList()
    }
}