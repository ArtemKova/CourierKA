package com.ka.courierka.navigation

sealed class Routes (val routes: String) {
    data object Login: Routes("Login")
    data object Forgot: Routes("Forgot")
    data object Register: Routes("Register")
    data object User: Routes("User")
    data object Order: Routes("Order")
    data object NewOrder: Routes("NewOrder")
    data object StatusUser: Routes("StatusUser")
}