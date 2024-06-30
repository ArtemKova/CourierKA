package com.ka.courierka.di

import com.google.gson.annotations.SerializedName

data class Typeorder(
    @SerializedName("id")
    val id: String,
    @SerializedName("typeorder")
    val typeorder: String
)
