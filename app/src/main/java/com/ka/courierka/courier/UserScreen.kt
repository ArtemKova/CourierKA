package com.ka.courierka.courier


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ka.courierka.tools.Constants.Companion.round
import com.ka.courierka.R
import com.ka.courierka.navigation.Routes
import com.ka.courierka.tools.Constants.Companion.borderSize
import com.ka.courierka.tools.Constants.Companion.buttonFontSize
import com.ka.courierka.tools.Constants.Companion.fontSizeInOrder
import com.ka.courierka.tools.Constants.Companion.heightInOrder
import com.ka.courierka.tools.Constants.Companion.padding


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun UserScreen(
    navController: NavHostController,
    userId:String?,
    viewModel: UsersViewModel = hiltViewModel()
) {
    var oo = stringResource(id = R.string.oldOrder)
    val oldorder = remember { mutableStateOf(oo) }
    val orders = viewModel.getOrderes().observeAsState(listOf()).value
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column {
            TopAppBar(
                title = { Text(stringResource(id = R.string.select_an_order), fontSize = buttonFontSize.sp) },
                actions = {
                    IconButton({
                        viewModel.logout()
                        navController.navigate(route = Routes.Login.routes)
                    }) { Icon(Icons.Filled.Home, contentDescription = "О приложении")}
                    IconButton({
                        navController.navigate(route = Routes.StatusUser.routes + "$userId")
                    }) { Icon(Icons.Filled.Settings, contentDescription = "Поиск") }
                },
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { navController.navigate(Routes.NewOrder.routes + "$userId") },
                shape = RoundedCornerShape(round.dp),
                modifier = Modifier.padding(padding.dp)
            ) { Text(stringResource(id = R.string.new_order), fontSize = buttonFontSize.sp) }
            Button(
                onClick = {
                    oldorder.value = deliv(oldorder.value)
                },
                shape = RoundedCornerShape(round.dp),
                modifier = Modifier.padding(padding.dp)
            ) { Text(oldorder.value, fontSize = 28.sp) }
        }
        LazyColumn {
            items(orders) { order ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.LightGray)
                        .border(width = borderSize.dp, color = Color.White)
                        .height(heightInOrder.dp)
                        .clickable(onClick = { navController.navigate(route = Routes.Order.routes + "${order.id},${userId}") }),
                    horizontalArrangement = Arrangement.SpaceBetween,

                    ) {
                    Text(
                        text = String.format(
                            "%s %s, %s",
                            order.name,
                            order.phone,
                            order.time
                        ),
                        fontSize = fontSizeInOrder.sp
                    )
                    Box(
                        modifier = Modifier
                            .size(heightInOrder.dp)
                            .background(color = Color.Red)
                    )
                }
            }
        }
    }


}
fun deliv(nameButton: String):String{
    var nameButton= nameButton
    if (nameButton.equals("Old order")) {
        nameButton = "Orders"
    } else {
        nameButton = "Old order"
    }
    return nameButton
}