package com.ka.courierka.coin

import com.google.gson.annotations.SerializedName

data class Typeorder(
    @SerializedName("id")
    val id: String,
    @SerializedName("typeorder")
    val typeorder: String
)
