package com.ka.courierka.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ka.courierka.courier.StatusUserScreen
import com.ka.courierka.courier.TypeViewModel
import com.ka.courierka.courier.UserScreen
import com.ka.courierka.courier.UsersViewModel
import com.ka.courierka.forgot.ForgotScreen
import com.ka.courierka.forgot.ResetPasswordViewModel
import com.ka.courierka.login.LoginScreen
import com.ka.courierka.login.LoginViewModel
import com.ka.courierka.order.Order
import com.ka.courierka.order.neworder.NewOrderScreen
import com.ka.courierka.order.neworder.NewOrderViewModel
import com.ka.courierka.order.order.OrderScreen
import com.ka.courierka.order.order.OrderViewModel
import com.ka.courierka.register.RegScreen
import com.ka.courierka.register.RegistrationViewModel


@Composable
internal fun Navigation(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Routes.Login.routes) {
        composable(Routes.Login.routes) {
            val loginViewModel: LoginViewModel = viewModel()
            LoginScreen(navController = navController, loginViewModel)
        }
        composable(Routes.Forgot.routes + "/{email}") { stackEntry ->
            val email = stackEntry.arguments?.getString("email")
            val resetPasswordViewModel: ResetPasswordViewModel = viewModel()
            ForgotScreen(navController = navController, email, resetPasswordViewModel)
        }
        composable(Routes.Register.routes) {
            val registrationViewModel: RegistrationViewModel = viewModel()
            RegScreen(navController = navController, registrationViewModel)
        }
        composable(Routes.User.routes + "/{userId}") { stackEntry ->
            val userId = stackEntry.arguments?.getString("userId")
            val usersViewModel: UsersViewModel = hiltViewModel()
            UserScreen(navController = navController, userId, usersViewModel)
        }
        composable(Routes.Order.routes + "/{order}" + "/{curid}") { stackEntry ->
            val order = stackEntry.arguments?.getString("order")
            val currentId = stackEntry.arguments?.getString("curid")
            val orderViewModel: OrderViewModel = hiltViewModel()
            OrderScreen(navController = navController, order, currentId, orderViewModel)
        }
        composable(Routes.NewOrder.routes + "/{curid}") { stackEntry ->
            val currentId = stackEntry.arguments?.getString("curid")
            val neworderViewModel: NewOrderViewModel = viewModel()
            val typeViewModel: TypeViewModel = hiltViewModel()
            NewOrderScreen(navController = navController, currentId, neworderViewModel, typeViewModel)
        }
        composable(Routes.StatusUser.routes + "/{userId}") { stackEntry ->
            val userId = stackEntry.arguments?.getString("userId")
            val usersViewModel: UsersViewModel = hiltViewModel()
            StatusUserScreen(navController = navController, userId, usersViewModel)
        }

    }
}

