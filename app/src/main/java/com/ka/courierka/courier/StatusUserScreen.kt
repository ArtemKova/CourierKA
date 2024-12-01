package com.ka.courierka.courier


import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import com.ka.courierka.tools.Constants.Companion.padding
import com.ka.courierka.R
import com.ka.courierka.navigation.Routes
import com.ka.courierka.tools.Constants.Companion.bigFontSize
import com.ka.courierka.tools.Constants.Companion.buttonFontSize

@Composable
internal fun StatusUserScreen(
    navController: NavHostController,
    userId: String?,
    viewModel: UsersViewModel = hiltViewModel()
) {
    val users = remember {
        mutableStateOf(User("", "", "", 0, false, false, "", ""))
    }
    var user = viewModel.getUser().value
    var customer = viewModel.users.observeAsState().value
    Log.d("Customer110", "$customer")
    if (users.value.id == "" && customer!=null) {
        users.value = customer?.get(0)!!
    }

    val selected = remember { mutableStateOf(users.value.courier ?: true) }
    Column {

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.user),
                fontSize = bigFontSize.sp,
                color = colorResource(id = R.color.purple_500),
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            var name = remember { mutableStateOf(users.value.name) }
            if (name.value == "") {
                name.value = users.value.name
            }
            TextField(
                value = name.value, onValueChange = { changedName ->
                    name.value = changedName
                    users.value.name = name.value
                },  modifier = Modifier
                    .padding(padding.dp)
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
                },  modifier = Modifier
                    .padding(padding.dp)
                    .fillMaxWidth()
            )
            val age = remember { mutableStateOf(users.value.age.toString()) }
            if (age.value == ""|| age.value == "0") {
               age.value = users.value.age.toString()
            }
            TextField(
                value = age.value, onValueChange = { changedAge ->
                    age.value = changedAge
                    users.value.age = age.value.toInt()
                },
                modifier = Modifier
                    .padding(padding.dp)
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
                    .padding(padding.dp)
                    .fillMaxWidth()
            )
            var typeUser = remember { mutableStateOf(users.value.typeUser) }
            if (selected.value) {
                typeUser.value = "Courier"
            } else {
                typeUser.value = "Customer"
            }
            users.value.typeUser = typeUser.value
            Text(
                text = users.value.typeUser,
                modifier = Modifier
                    .selectable(
                        selected = selected.value,
                        onClick = { selected.value = !selected.value }
                    )
                    .padding(padding.dp)
                    .fillMaxWidth()
            )
            Row {
                Button(onClick = {
                    userId?.let {
                        viewModel.setChangeUserData(
                            it,
                            users.value.name,
                            users.value.lastName,
                            users.value.age,
                            users.value.city,
                            selected.value
                        )
                    }
                    navController.navigate(route = Routes.User.routes + "/${userId}")
                }, modifier = Modifier.padding(padding.dp)) {
                    Text(stringResource(id = R.string.save), fontSize = buttonFontSize.sp)
                }
                Button(
                    onClick = { navController.navigate(route = Routes.User.routes + "/${userId}") },
                    modifier = Modifier.padding(
                        padding.dp
                    )
                ) {
                    Text(stringResource(id = R.string.cancel), fontSize = buttonFontSize.sp)


                }
            }
        }


    }
}