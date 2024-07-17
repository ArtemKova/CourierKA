package com.ka.courierka.order.order

import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ka.courierka.R
import com.ka.courierka.courier.UserFragment
import com.ka.courierka.helper.isCorrectDestinationNow
import com.example.data.data.Order

private const val ORDER = "order"
private const val CUR_ID = "currentUserId"


class OrderFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var order: Order? = null
    private var currentUserId: String? = null

    private var requiredDestinationId = 0
    private lateinit var viewModel: OrderViewModel
    private lateinit var id: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            order = it.getSerializable(ORDER) as Order?
            currentUserId = it.getString(CUR_ID)
        }
        viewModel = ViewModelProvider(this)[OrderViewModel::class.java]
        id = order?.id.toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requiredDestinationId = R.id.orderFragment
          var view = ComposeView(requireContext()).apply {
              setContent {
                  Column(
                      modifier = Modifier
                          .fillMaxSize(),
                      horizontalAlignment = Alignment.CenterHorizontally
                  ) {
                      Text(
                          text = stringResource(id = R.string.order),
                          fontSize = 40.sp,
                          color = colorResource(id = R.color.purple_500),
                          modifier = Modifier
                              .fillMaxWidth(),
                          textAlign = TextAlign.Center
                      )
                      order?.let {
                          Text(
                              text = it.name, modifier = Modifier
                                  .padding(10.dp)
                                  .fillMaxWidth()
                          )
                          Text(text = it.adress, modifier = Modifier
                              .padding(10.dp)
                              .fillMaxWidth()
                          )
                          Text(text = it.recadress,
                          modifier = Modifier
                              .padding(10.dp)
                              .fillMaxWidth()
                          )
                          Text(text = it.phone,
                          modifier = Modifier
                              .padding(10.dp)
                              .fillMaxWidth()
                          )
                          Text(text= it.time,
                              modifier = Modifier
                                  .padding(10.dp)
                                  .fillMaxWidth()
                          )
                          Text(text = it.isPay,
                              modifier = Modifier
                                  .padding(10.dp)
                                  .fillMaxWidth()
                          )
                          Text(text = it.city,
                          modifier = Modifier
                              .padding(10.dp)
                              .fillMaxWidth()
                          )
                          Text(text = it.typeOrder,
                              modifier = Modifier
                                  .padding(10.dp)
                                  .fillMaxWidth()
                          )
                      }
                     Button(
                          onClick = {
                              order?.let { currentUserId?.let { it1 ->
                                  viewModel.setGetOrder(it.id,
                                      it1
                                  )
                              } }
                              currentUserId?.let { goToUser(it) }
                          },
                          shape = RoundedCornerShape(10.dp),
                          modifier = Modifier
                              .padding(10.dp)
                              .fillMaxWidth()
                      ) { Text(stringResource(id = R.string.get_order), fontSize = 28.sp) }
                  }
              }
          }
        return view
    }
    private fun goToUser(id: String) {
        val currentDestination = findNavController().currentDestination?.id
        if (isCorrectDestinationNow(currentDestination, requiredDestinationId)) {
            val args = UserFragment.newInstance(id)
            findNavController().navigate(
                R.id.action_orderFragment_to_userFragment,
                args.arguments
            )
        }

    }

    companion object {

        @JvmStatic
        fun newInstance(
            order: Order,
            currentUserId: String
        ) =
            OrderFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ORDER, order)
                    putString(CUR_ID, currentUserId)
                }
            }
    }
}