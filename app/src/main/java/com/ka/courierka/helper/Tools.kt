package com.ka.courierka.helper

fun isCorrectDestinationNow(currentId: Int?, requiredId: Int) : Boolean {
    currentId?.let {
        return it == requiredId
    }
    return false
}