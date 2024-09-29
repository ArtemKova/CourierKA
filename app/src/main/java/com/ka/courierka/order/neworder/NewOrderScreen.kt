package com.ka.courierka.order.neworder


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHostController
import com.ka.courierka.R
import com.ka.courierka.courier.TypeViewModel
import com.ka.courierka.di.TypeOrder
import com.ka.courierka.di.repo.Resource
import com.ka.courierka.order.Order
import com.ka.courierka.tools.Constants.Companion.bigFontSize
import com.ka.courierka.tools.Constants.Companion.buttonFontSize
import com.ka.courierka.tools.Constants.Companion.padding
import com.ka.courierka.tools.Constants.Companion.round

@Composable
internal fun NewOrderScreen(
    navController: NavHostController,
    currentId: String?,
    viewModel: NewOrderViewModel = hiltViewModel(),
    viewModelType: TypeViewModel
) {
    val orders = rememberSaveable {
        mutableStateOf(Order("", "", "", "", "", "", "", "", "", "", ""))}
    val net = remember { mutableStateOf(MutableLiveData<Resource<List<TypeOrder>>>()) }
    net.value = viewModelType.modelItem
    var types = net.value.value?.data
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.order),
            fontSize = bigFontSize.sp,
            color = colorResource(id = R.color.purple_500),
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        TextField(
            value = orders.value.name, onValueChange = { name ->
                orders.value.name = name
            }, placeholder = { Text("Name") }, modifier = Modifier
                .padding(padding.dp)
                .fillMaxWidth()
        )
        TextField(
            value = orders.value.address, onValueChange = { adress ->
                orders.value.address = adress
            }, placeholder = { Text("Adress") }, modifier = Modifier
                .padding(padding.dp)
                .fillMaxWidth()
        )
        TextField(
            value = orders.value.recadress, onValueChange = { recadress ->
                orders.value.recadress = recadress
            }, placeholder = { Text("Client's Adress ") },
            modifier = Modifier
                .padding(padding.dp)
                .fillMaxWidth()
        )
        TextField(
            value = orders.value.phone, onValueChange = { phone ->
                orders.value.phone = phone
            }, placeholder = { Text("Phone") },
            modifier = Modifier
                .padding(padding.dp)
                .fillMaxWidth()
        )
        TextField(
            value = orders.value.time, onValueChange = { time ->
                orders.value.time = time
            }, placeholder = { Text("Time") },
            modifier = Modifier
                .padding(padding.dp)
                .fillMaxWidth()
        )
        TextField(
            value = orders.value.isPay, onValueChange = { isPay ->
                orders.value.isPay = isPay
            }, placeholder = { Text("Pay") },
            modifier = Modifier
                .padding(padding.dp)
                .fillMaxWidth()
        )
        TextField(
            value = orders.value.city, onValueChange = { city ->
                orders.value.city = city
            }, placeholder = { Text("City") },
            modifier = Modifier
                .padding(padding.dp)
                .fillMaxWidth()
        )
        TextField(
            value = orders.value.typeOrder, onValueChange = { typeOrder ->
                orders.value.typeOrder = typeOrder
            }, placeholder = { Text("Type Order") },
            modifier = Modifier
                .padding(padding.dp)
                .fillMaxWidth()
        )
        val id = viewModel.setOrderId()
        Button(
            onClick = {
                var newOrder = Order(
                    id ?: "id",
                    orders.value.name.trim(),
                    orders.value.phone.trim(),
                    orders.value.address.trim(),
                    orders.value.recadress.trim(),
                    currentId ?: " ",
                    orders.value.time.trim(),
                    orders.value.isPay.trim(),
                    "",
                    orders.value.city.trim(),
                    orders.value.typeOrder.trim(),
                    false
                )

                newOrder?.let {
                    viewModel.createOrder(it)
                    navController.navigateUp()
                }


            },
            shape = RoundedCornerShape(round.dp),
            modifier = Modifier
                .padding(padding.dp)
                .fillMaxWidth()
        ) { Text(stringResource(id = R.string.get_order), fontSize = buttonFontSize.sp) }
    }
}
