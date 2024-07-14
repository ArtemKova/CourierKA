package com.ka.courierka.dependencyinjection

import com.google.gson.annotations.SerializedName

data class TypeOrder(
    @SerializedName("id")
    val id: String,
    @SerializedName("typeorder")
    val typeorder: String
)
