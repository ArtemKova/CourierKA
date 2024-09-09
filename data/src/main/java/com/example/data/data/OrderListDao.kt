package com.example.data.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface OrderListDao {

    @Query("SELECT * FROM order_items")
    fun getOrderList(): LiveData<List<OrderItemDBModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addOrderItem(orderItemDBModel: OrderItemDBModel)

    @Query("DELETE FROM order_items WHERE id=:orderId")
    suspend fun deleteOrderItem(orderId:String)

    @Query("SELECT * FROM order_items WHERE id=:orderId LIMIT 1")
     fun getOrderItem(orderId:String):Order

    @Query("DELETE FROM order_items")
    suspend fun delete()


}