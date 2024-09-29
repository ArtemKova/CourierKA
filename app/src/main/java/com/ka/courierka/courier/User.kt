package com.ka.courierka.courier

import android.os.Parcelable

data class User(
    var id: String ,
    var name: String ,
    var lastName: String,
    var age: Int = 0,
    var isOnLine: Boolean = false,
    var courier: Boolean = false,
    var city: String = "",
    var typeUser: String = ""
)

