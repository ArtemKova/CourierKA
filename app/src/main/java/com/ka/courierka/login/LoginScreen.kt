package com.ka.courierka.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ka.courierka.tools.Constants.Companion.padding
import com.ka.courierka.tools.Constants.Companion.round
import com.ka.courierka.R
import com.ka.courierka.navigation.Routes
import com.ka.courierka.tools.Constants.Companion.bigFontSize
import com.ka.courierka.tools.Constants.Companion.buttonFontSize
import com.ka.courierka.tools.Constants.Companion.textField

@Composable
internal fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModel = hiltViewModel()
) {

    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val login = viewModel.userId.observeAsState().value
    if (login != null) {
        navController.navigate(Routes.User.routes + "/${login.uid}")
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.courier),
            fontSize = bigFontSize.sp,
            color = colorResource(id = R.color.purple_500),
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        TextField(value = email.value, onValueChange = { email2 ->
            email.value = email2
        }, placeholder = { Text("Email", fontSize = textField.sp) }, modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
        )
        TextField(value = password.value, onValueChange = { pas ->
            password.value = pas
        }, placeholder = { Text("Password", fontSize = textField.sp) }, modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
        )
        Button(
            onClick = {
                viewModel.login(email.value, password.value)
            },
            shape = RoundedCornerShape(round.dp),
            modifier = Modifier
                .padding(padding.dp)
                .fillMaxWidth()
        ) { Text(stringResource(id = R.string.login), fontSize = buttonFontSize.sp) }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.forgot_password),
                fontSize = textField.sp,
                modifier = Modifier
                    .clickable(onClick = {
                        if (email.value.isEmpty()){email.value = ""}
                        navController.navigate(route = Routes.Forgot.routes + "/${email.value}") })
                    .padding(padding.dp)
            )
            Text(
                text = stringResource(id = R.string.register),
                fontSize = textField.sp,
                modifier = Modifier
                    .clickable(onClick = { navController.navigate(route = Routes.Register.routes) })
                    .padding(padding.dp)
            )
        }
    }
}

