package com.ka.courierka.courier

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
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
import com.ka.courierka.tools.Constants.Companion.big_font_size
import com.ka.courierka.tools.Constants.Companion.button_font_size
import com.ka.courierka.tools.Constants.Companion.padding
import com.ka.courierka.R
import com.ka.courierka.navigation.Routes

@Composable
internal fun StatusUserScreen(
    navController: NavHostController,
    userId: String?,
    viewModel: UsersViewModel = hiltViewModel()
) {
    val name = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }
    val age = remember { mutableStateOf("") }
    val city = remember { mutableStateOf("") }
    val ageInt = remember { mutableStateOf(0) }
    val type_user = remember { mutableStateOf("") }
    var user = viewModel.getUser()
    var courier = viewModel.getCourier().observeAsState(listOf()).value
    var customer = viewModel.users.observeAsState().value
    val selected = remember { mutableStateOf(customer?.get(0)?.courier?:true) }
    Column {

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.user),
                fontSize = big_font_size.sp,
                color = colorResource(id = R.color.purple_500),
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            TextField(
                value = name.value, onValueChange = { name2 ->
                    name.value = name2
                }, placeholder = { Text("${customer?.get(0)?.name?:"-"}") }, modifier = Modifier
                    .padding(padding.dp)
                    .fillMaxWidth()
            )
            TextField(
                value = lastName.value, onValueChange = { lastName2 ->
                    lastName.value = lastName2
                }, placeholder = { Text("${customer?.get(0)?.lastName?:"-"}") }, modifier = Modifier
                    .padding(padding.dp)
                    .fillMaxWidth()
            )
            TextField(
                value = "${age.value}", onValueChange = { age2 ->
                    age.value = age2
                    if (age.value == ""){age.value = "0"}
                    try {
                        ageInt.value = age.value.toInt()
                    } catch (nfe: NumberFormatException) {
                        ageInt.value = 0
                    }
                }, placeholder = { Text("${customer?.get(0)?.age?.toString()?:"0"}") },
                modifier = Modifier
                    .padding(padding.dp)
                    .fillMaxWidth()
            )
            TextField(
                value = city.value, onValueChange = { city2 ->
                    city.value = city2
                }, placeholder = { Text("${customer?.get(0)?.city?:"- "}") },
                modifier = Modifier
                    .padding(padding.dp)
                    .fillMaxWidth()
            )
            var type_user = ""
            if (selected.value) {
                type_user = "Courier"
            } else {
                type_user = "Customer"
            }
            Text(
                text = type_user,
                modifier = Modifier.selectable(
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
                                name.value,
                                lastName.value,
                                ageInt.value,
                                city.value,
                                selected.value
                            )
                        }
                    navController.navigate(route = Routes.User.routes + "/${userId}")
                }, modifier = Modifier.padding(padding.dp)) {
                    Text(stringResource(id = R.string.save), fontSize = button_font_size.sp)
                }
                Button(onClick = {navController.navigate(route = Routes.User.routes + "/${userId}")}, modifier = Modifier.padding(
                    padding.dp)) {
                    Text(stringResource(id = R.string.cancel), fontSize = button_font_size.sp)

                    
                }
            }
        }


    }
}