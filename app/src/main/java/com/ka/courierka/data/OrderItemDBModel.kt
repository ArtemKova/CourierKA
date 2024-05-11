package com.ka.courierka.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "order_items")
data class OrderItemDBModel(
    @PrimaryKey
    val id: String,
    val name: String,
    val phone: String,
    val adress: String,
    val recadress: String,
    val customer_id: String,
    val time: String,
    val isPay: String,
    val courier: String,
    val city: String,
    val typeOrder: String,
    val delivered: Boolean
)