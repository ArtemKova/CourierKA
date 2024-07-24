package com.ka.courierka.di

import com.google.gson.annotations.SerializedName

data class TypeOrder(
    @SerializedName("id")
    val id: String,
    @SerializedName("typeorder")
    val typeorder: String
)
