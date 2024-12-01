package com.ka.courierka.register

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ka.courierka.R
import com.ka.courierka.courier.User
import com.ka.courierka.navigation.Routes
import com.ka.courierka.tools.Constants

@Composable
internal fun RegScreen(
    navController: NavHostController,
    viewModel: RegistrationViewModel = hiltViewModel()
) {

    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val users = remember {
        mutableStateOf(User("", "", "", 0, false, false, "", ""))
    }
    val age = remember { mutableStateOf("") }
    val expanded = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.courier),
            fontSize = 40.sp,
            color = colorResource(id = R.color.purple_500),
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        TextField(value = email.value, onValueChange = { email2 ->
            email.value = email2
        }, placeholder = { Text("Email") }, modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
        )
        TextField(value = password.value, onValueChange = { pas ->
            password.value = pas

        }, placeholder = { Text("Password") }, modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
        )
        var name = remember { mutableStateOf(users.value.name) }
        if (name.value == "") {
            name.value = users.value.name
        }
        TextField(
            value = name.value, onValueChange = { changedName ->
                name.value = changedName
                users.value.name = name.value
            }, modifier = Modifier
                .padding(Constants.padding.dp)
                .fillMaxWidth()
        )
        var lastName = remember { mutableStateOf(users.value.lastName) }
        if (lastName.value == "") {
            lastName.value = users.value.lastName
        }
        TextField(
            value = lastName.value, onValueChange = { changedLastName ->
                lastName.value = changedLastName
                users.value.lastName = lastName.value
            }, modifier = Modifier
                .padding(Constants.padding.dp)
                .fillMaxWidth()
        )
        val age = remember { mutableStateOf(users.value.age.toString()) }
        if (age.value == "" || age.value == "0") {
            age.value = users.value.age.toString()
        }
        TextField(
            value = age.value, onValueChange = { changedAge ->
                age.value = changedAge
                users.value.age = age.value.toInt()
            },
            modifier = Modifier
                .padding(Constants.padding.dp)
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        var city = remember { mutableStateOf(users.value.city) }
        if (city.value == "") {
            city.value = users.value.city
        }
        TextField(
            value = city.value, onValueChange = { changedCity ->
                city.value = changedCity
                users.value.city = city.value
            },
            modifier = Modifier
                .padding(Constants.padding.dp)
                .fillMaxWidth()
        )
        Box {
            IconButton(onClick = { expanded.value = true }) {
                Icon(Icons.Default.MoreVert, contentDescription = "Показать меню")
            }
            var typeUser = remember { mutableStateOf(users.value.typeUser) }
            var courier = remember { mutableStateOf(false) }
            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false }
            ) {
                DropdownMenuItem(
                    onClick = { courier.value = false },
                    text = {
                        Text(stringResource(id = R.string.courier))
                        typeUser.value = stringResource(id = R.string.courier)
                    }
                )
                DropdownMenuItem(
                    onClick = { courier.value = true },
                    text = {
                        Text(stringResource(id = R.string.customer))
                        typeUser.value = stringResource(id = R.string.customer)
                    }
                )
            }
            users.value.courier = courier.value
            users.value.typeUser = typeUser.value
        }
        Button(
            onClick = {
                viewModel.signUp(
                    email.value,
                    password.value,
                    users.value.name,
                    users.value.lastName,
                    users.value.age,
                    users.value.city,
                    users.value.courier
                )
                navController.navigate(route = Routes.Login.routes)
            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) { Text(stringResource(id = R.string.sign_up), fontSize = 28.sp) }


    }
}