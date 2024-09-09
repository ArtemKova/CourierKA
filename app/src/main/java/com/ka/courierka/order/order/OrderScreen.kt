package com.ka.courierka.order.order


import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
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
import com.ka.courierka.tools.Constants.Companion.big_font_size
import com.ka.courierka.tools.Constants.Companion.button_font_size
import com.ka.courierka.tools.Constants.Companion.padding
import com.ka.courierka.tools.Constants.Companion.round
import com.ka.courierka.R

@Composable
internal fun OrderScreen(
    navController: NavHostController,
    orderId: String?,
    currentId: String?,
    viewModel: OrderViewModel = hiltViewModel()
) {
    val orderes = viewModel.getOrders().observeAsState(listOf()).value
    val orders = orderes.filter { it.id.equals(orderId) }
    orders.let { Log.d("itemCoin1234", "Order: ${it}") }
    if (orders.isNotEmpty()) {
        var order = rememberSaveable {
            mutableStateOf(orders[0])
        }
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
            order.value.let {
                EnterText(it.name)
                EnterText(it.adress)
                EnterText(it.recadress)
                EnterText(it.phone)
                EnterText(it.time)
                EnterText(it.isPay)
                EnterText(it.city)
                EnterText(it.typeOrder)
            }
            Button(
                onClick = {
                    currentId?.let {
                        order?.let {
                            viewModel.setGetOrder(
                                it.value.id,
                                currentId
                            )
                        }
                        navController.navigateUp()
                    }
                },
                shape = RoundedCornerShape(round.dp),
                modifier = Modifier
                    .padding(padding.dp)
                    .fillMaxWidth()
            ) { Text(stringResource(id = R.string.get_order), fontSize = button_font_size.sp) }
        }
    }

}
@Composable
fun EnterText (element: String){
    Text(
        text = element, modifier = Modifier
            .padding(padding.dp)
            .fillMaxWidth()
    )
}