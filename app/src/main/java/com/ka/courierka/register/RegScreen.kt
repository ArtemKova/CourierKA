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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ka.courierka.R
import com.ka.courierka.navigation.Routes

@Composable
internal fun RegScreen(
    navController: NavHostController,
    viewModel: RegistrationViewModel
){

    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val name = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }
    val age = remember { mutableStateOf(0) }
    val city = remember { mutableStateOf("") }
    val typeCourier = remember { mutableStateOf(false) }
    var expanded = remember { mutableStateOf(false) }
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
            value = name.value,
            onValueChange = { nam ->
                name.value = nam
//                        pass = name.value
            },
            placeholder = { Text(stringResource(id = R.string.name)) },
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        )
        TextField(
            value = lastName.value,
            onValueChange = { pas ->
                lastName.value = pas
//                        pass = lastName.value
            },
            placeholder = { Text(stringResource(id = R.string.last_name)) },
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        )
        TextField(
            value = age.value.toString(),
            onValueChange = { pas ->
                age.value = pas.toInt()
//                        pass = age.value
            },
            placeholder = { Text(stringResource(id = R.string.age)) },
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        TextField(
            value = city.value,
            onValueChange = { pas ->
                city.value = pas

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
                    onClick = { typeCourier.value = false },
                    text = { Text(stringResource(id = R.string.courier)) }
                )
                DropdownMenuItem(
                    onClick = { typeCourier.value = true },
                    text = { Text(stringResource(id = R.string.customer)) }
                )
            }
        }
        Button(
            onClick = {
                viewModel.signUp(
                    email.value,
                    password.value,
                    name.value,
                    lastName.value,
                    age.value,
                    city.value,
                    typeCourier.value
                )
                navController.navigate(route = Routes.Login.routes)
//                goToLogin(email.value, password.value)
            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) { Text(stringResource(id = R.string.sign_up), fontSize = 28.sp) }


    }
}