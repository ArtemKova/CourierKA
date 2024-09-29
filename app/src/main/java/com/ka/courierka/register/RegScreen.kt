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

@Composable
internal fun RegScreen(
    navController: NavHostController,
    viewModel: RegistrationViewModel = hiltViewModel()
){

    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val users = rememberSaveable {
        mutableStateOf(User("","", "", 0, false, false, "", ""))}
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
        TextField(
            value = users.value.name,
            onValueChange = { name ->
                users.value.name = name
            },
            placeholder = { Text(stringResource(id = R.string.name)) },
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        )
        TextField(
            value = users.value.lastName,
            onValueChange = { changedLastName ->
                users.value.lastName= changedLastName
            },
            placeholder = { Text(stringResource(id = R.string.last_name)) },
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        )
        TextField(
            value = "${users.value.age}",
            onValueChange = { changedAge ->
                if (changedAge == ""){users.value.age = 0}
                else {
                    users.value.age = changedAge.toInt()
                }
            },
            placeholder = { Text(stringResource(id = R.string.age)) },
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        TextField(
            value = users.value.city,
            onValueChange = { changedCity ->
                users.value.city = changedCity

            },
            placeholder = { Text(stringResource(id = R.string.city)) },
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        )
        Box {
            IconButton(onClick = { expanded.value = true }) {
                Icon(Icons.Default.MoreVert, contentDescription = "Показать меню")
            }
            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false }
            ) {
                DropdownMenuItem(
                    onClick = { users.value.courier = false },
                    text = { Text(stringResource(id = R.string.courier)) }
                )
                DropdownMenuItem(
                    onClick = { users.value.courier = true },
                    text = { Text(stringResource(id = R.string.customer)) }
                )
            }
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