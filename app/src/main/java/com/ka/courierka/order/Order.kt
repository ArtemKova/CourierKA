package com.ka.courierka.order

import java.io.Serializable

data class Order(
    var id: String = "",
    var name: String = "",
    var phone: String = "",
    var adress: String = "",
    var recadress: String = "",
    var customer_id: String = "",
    var time: String = "",
    var isPay: String = "",
    var courier: String = "",
    var city: String = "",
    var typeOrder: String = "",
    var delivered: Boolean = false
) : Serializable
