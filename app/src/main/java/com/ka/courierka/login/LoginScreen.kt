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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ka.courierka.tools.Constants.Companion.big_font_size
import com.ka.courierka.tools.Constants.Companion.button_font_size
import com.ka.courierka.tools.Constants.Companion.padding
import com.ka.courierka.tools.Constants.Companion.round
import com.ka.courierka.tools.Constants.Companion.text_field
import com.ka.courierka.R
import com.ka.courierka.navigation.Routes

@Composable
internal fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModel = viewModel()
) {

    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val login = viewModel.userid.observeAsState().value

    if(login != null){
        navController.navigate(Routes.User.routes+"/${login.uid}")
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.courier),
            fontSize = big_font_size.sp,
            color = colorResource(id = R.color.purple_500),
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        TextField(value = email.value, onValueChange = { email2 ->
            email.value = email2
        }, placeholder = { Text("Email",fontSize = text_field.sp) }, modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth())
        TextField(value = password.value, onValueChange = { pas ->
            password.value = pas
        }, placeholder = { Text("Password",fontSize = text_field.sp) }, modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth())
        Button(
            onClick = {
                viewModel.login(email.value, password.value)
                      },
            shape = RoundedCornerShape(round.dp),
            modifier = Modifier
                .padding(padding.dp)
                .fillMaxWidth()
        ) { Text(stringResource(id = R.string.login), fontSize = button_font_size.sp) }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.forgot_password),
                fontSize = text_field.sp,
                modifier = Modifier
                    .clickable(onClick = { navController.navigate(route = Routes.Forgot.routes + "/${email.value}") })
                    .padding(padding.dp)
            )
            Text(
                text = stringResource(id = R.string.register),
                fontSize = text_field.sp,
                modifier = Modifier
                    .clickable(onClick = { navController.navigate(route = Routes.Register.routes) })
                    .padding(padding.dp)
            )
        }
    }
}

