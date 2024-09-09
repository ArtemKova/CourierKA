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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHostController
import com.ka.courierka.R
import com.ka.courierka.courier.TypeViewModel
import com.ka.courierka.di.TypeOrder
import com.ka.courierka.di.repo.Resource
import com.ka.courierka.order.Order
import com.ka.courierka.tools.Constants.Companion.big_font_size
import com.ka.courierka.tools.Constants.Companion.button_font_size
import com.ka.courierka.tools.Constants.Companion.padding
import com.ka.courierka.tools.Constants.Companion.round

@Composable
internal fun NewOrderScreen(
    navController: NavHostController,
    currentId: String?,
    viewModel: NewOrderViewModel,
    viewModel1: TypeViewModel
) {
    val name = remember { mutableStateOf("") }
    val adress = remember { mutableStateOf("") }
    val recadress = remember { mutableStateOf("") }
    val phone = remember { mutableStateOf("") }
    val time = remember { mutableStateOf("") }
    val isPay = remember { mutableStateOf("") }
    val city = remember { mutableStateOf("") }
    val typeOrder = remember { mutableStateOf("") }
    val net = remember { mutableStateOf(MutableLiveData<Resource<List<TypeOrder>>>())}
    net.value = viewModel1.modelItem
    var types = net.value.value?.data
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.order),
            fontSize = big_font_size.sp,
            color = colorResource(id = R.color.purple_500),
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        TextField(
            value = name.value, onValueChange = { name2 ->
                name.value = name2
            }, placeholder = { Text("Name") }, modifier = Modifier
                .padding(padding.dp)
                .fillMaxWidth()
        )
        TextField(
            value = adress.value, onValueChange = { adress2 ->
                adress.value = adress2
            }, placeholder = { Text("Adress") }, modifier = Modifier
                .padding(padding.dp)
                .fillMaxWidth()
        )
        TextField(
            value = recadress.value, onValueChange = { recadress2 ->
                recadress.value = recadress2
            }, placeholder = { Text("Client's Adress ") },
            modifier = Modifier
                .padding(padding.dp)
                .fillMaxWidth()
        )
        TextField(
            value = phone.value, onValueChange = { phone2 ->
                phone.value = phone2
            }, placeholder = { Text("Phone") },
            modifier = Modifier
                .padding(padding.dp)
                .fillMaxWidth()
        )
        TextField(
            value = time.value, onValueChange = { time2 ->
                time.value = time2
            }, placeholder = { Text("Time") },
            modifier = Modifier
                .padding(padding.dp)
                .fillMaxWidth()
        )
        TextField(
            value = isPay.value, onValueChange = { isPay2 ->
                isPay.value = isPay2
            }, placeholder = { Text("Pay") },
            modifier = Modifier
                .padding(padding.dp)
                .fillMaxWidth()
        )
        TextField(
            value = city.value, onValueChange = { city2 ->
                city.value = city2
            }, placeholder = { Text("City") },
            modifier = Modifier
                .padding(padding.dp)
                .fillMaxWidth()
        )
        TextField(
            value = typeOrder.value, onValueChange = { typeOrder2 ->
                typeOrder.value = typeOrder2
            }, placeholder = { Text("Type Order") },
            modifier = Modifier
                .padding(padding.dp)
                .fillMaxWidth()
        )
        val id = viewModel.setOrderId()
        Button(
            onClick = {
                var newOrder = Order(
                    id ?:"id",
                    name.value.trim(),
                    phone.value.trim(),
                    adress.value.trim(),
                    recadress.value.trim(),
                    currentId?:" ",
                    time.value.trim(),
                    isPay.value.trim(),
                    "",
                   city.value.trim(),
                    typeOrder.value.trim(),
                    false
                )

               newOrder?.let {
                    viewModel.createOrder(it)
                }
                navController.navigateUp()

    },
    shape = RoundedCornerShape(round.dp),
    modifier = Modifier
        .padding(padding.dp)
        .fillMaxWidth()
    ) { Text(stringResource(id = R.string.get_order), fontSize = button_font_size.sp) }
}
}
