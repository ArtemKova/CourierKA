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
import androidx.compose.runtime.saveable.rememberSaveable
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
    val users = rememberSaveable {
        mutableStateOf(User("","", "", 0, false, false, "", ""))

    }
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
                fontSize = bigFontSize.sp,
                color = colorResource(id = R.color.purple_500),
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            TextField(
                value = users.value.name , onValueChange = { changedName ->
                    users.value.name = changedName
                }, placeholder = { Text(customer?.get(0)?.name?:"-") }, modifier = Modifier
                    .padding(padding.dp)
                    .fillMaxWidth()
            )
            TextField(
                value = users.value.lastName, onValueChange = { changedLastName ->
                    users.value.lastName = changedLastName
                }, placeholder = { Text(customer?.get(0)?.lastName?:"-") }, modifier = Modifier
                    .padding(padding.dp)
                    .fillMaxWidth()
            )
            TextField(
                value = "${users.value.age}", onValueChange = { changedAge ->
                    users.value.age = changedAge.toInt()

                }, placeholder = { Text("${customer?.get(0)?.age?:"0"}") },
                modifier = Modifier
                    .padding(padding.dp)
                    .fillMaxWidth()
            )
            TextField(
                value = users.value.city, onValueChange = { changedCity ->
                    users.value.city = changedCity
                }, placeholder = { Text(customer?.get(0)?.city?:"-") },
                modifier = Modifier
                    .padding(padding.dp)
                    .fillMaxWidth()
            )

            if (selected.value) {
                users.value.typeUser = "Courier"
            } else {
                users.value.typeUser = "Customer"
            }
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
                Button(onClick = {navController.navigate(route = Routes.User.routes + "/${userId}")}, modifier = Modifier.padding(
                    padding.dp)) {
                    Text(stringResource(id = R.string.cancel), fontSize = buttonFontSize.sp)

                    
                }
            }
        }


    }
}