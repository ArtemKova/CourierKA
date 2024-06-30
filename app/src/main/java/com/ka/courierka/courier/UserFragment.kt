package com.ka.courierka.courier

import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.LinearLayout
import androidx.compose.foundation.background
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
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ka.courierka.R
import com.ka.courierka.di.repo.TypeViewModel
import com.ka.courierka.helper.isCorrectDestinationNow
import com.ka.courierka.order.neworder.NewOrderFragment
import com.ka.courierka.order.Order
import com.ka.courierka.order.order.OrderFragment
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.scope.Scope

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val CUR_ID = "currentUserId"


class UserFragment : Fragment(), AndroidScopeComponent {
    override val scope: Scope by fragmentScope()
    private val viewModel1 by viewModel<TypeViewModel>()

    // TODO: Rename and change types of parameters
    private var requiredDestinationId = 0
    private var typeOrders = arrayListOf<String>()
    private lateinit var recyclerViewUser: RecyclerView
    private lateinit var usersAdapter: UsersAdapter
    private lateinit var currentUserId: String
    private lateinit var viewModel: UsersViewModel
    private var deliv: Boolean = false
    var orders = mutableListOf<Order>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            currentUserId = it.getString(CUR_ID).toString()
        }
        viewModel = ViewModelProvider(this)[UsersViewModel::class.java]
        viewModel.setUserOnLine(true)

        viewModel1.doNetworkCall()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requiredDestinationId = R.id.userFragment
        observeLiveData()
        observeViewModel()
        var view = ComposeView(requireContext()).apply {
            setContent {
                var oo = stringResource(id = R.string.old_order)
                var oldorder = remember { mutableStateOf(oo) }
                Log.d("itemCoin1", "Order: ${orders}")
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = { goToNewOrder() },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.padding(10.dp)
                        ) { Text(stringResource(id = R.string.new_order), fontSize = 28.sp) }
                        Button(
                            onClick = {
                                observeViewModel()
                                if (oldorder.value.equals("Old order")) {
                                    oldorder.value = "Orders"
                                    deliv = false
                                } else {
                                    oldorder.value = "Old order"
                                    deliv = true
                                }
                                observeViewModel()
                            },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.padding(10.dp)
                        ) { Text(oldorder.value, fontSize = 28.sp) }
                    }
                    LazyColumn {
                        observeViewModel()
                        items(orders) { order ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp)
                                    .clickable(onClick = { goToOrder(order) }),
                                horizontalArrangement = Arrangement.SpaceEvenly,

                                ) {
                                Text(
                                    text = String.format(
                                        "%s %s, %s",
                                        order.name,
                                        order.phone,
                                        order.time
                                    )
                                )
                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .background(color = Color.Red)
                                )
                            }
                        }
                    }
                }
            }
        }


        return view
    }


    fun observeViewModel() {
        viewModel.getUser().observe(viewLifecycleOwner) {
            if (it == null) {
                goToLogin()
            }
        }
        viewModel.getOrders().observe(viewLifecycleOwner) {
            orders = it
//            for (order in it) {
//                if (deliv == order.delivered) {
//                    orders.add(order)
//                }
//            }
//            usersAdapter.setUsers(orders)
//            usersAdapter.onUserClickListener = object : UsersAdapter.OnUserClickListener {
//                override fun onUserClick(order: Order) {
//                    Log.d("Look_id4","${order.id}")
//                    goToOrder(order)
//                }
//            }
        }
        viewModel.getCourier().observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                viewModel.logout()
            }


//            if (it[0].courier){
//                linearLayout.visibility = GONE
//            }
//            else {
//                linearLayout.visibility = VISIBLE
//            }
        }
//        if (viewModel.getCourierK()){
//            linearLayout.visibility = GONE
//        }
//        else {
//            linearLayout.visibility = VISIBLE
//        }
    }

    private fun goToLogin() {
        val currentDestination = findNavController().currentDestination?.id
        if (isCorrectDestinationNow(currentDestination, requiredDestinationId)) {
            findNavController().navigate(R.id.action_userFragment_to_loginFragment)
        }
    }

    private fun goToNewOrder() {
        val currentDestination = findNavController().currentDestination?.id
        if (isCorrectDestinationNow(currentDestination, requiredDestinationId)) {
            val args = NewOrderFragment.newInstance(currentUserId, typeOrders)
            findNavController().navigate(
                R.id.action_userFragment_to_newOrderFragment,
                args.arguments
            )
        }
    }

    private fun goToOrder(order: Order) {
        val currentDestination = findNavController().currentDestination?.id
        if (isCorrectDestinationNow(currentDestination, requiredDestinationId)) {
            val args = OrderFragment.newInstance(
                order,
                currentUserId
            )
            findNavController().navigate(R.id.action_userFragment_to_orderFragment, args.arguments)
        }
    }

    private fun goToStatusUser() {
        val currentDestination = findNavController().currentDestination?.id
        if (isCorrectDestinationNow(currentDestination, requiredDestinationId)) {
            val args = StatusUser.newInstance(currentUserId, "")
            findNavController().navigate(R.id.action_userFragment_to_statusUser, args.arguments)
        }
    }
//    private fun goToChat(user:User){
//        val currentDestination = findNavController().currentDestination?.id
//        if (isCorrectDestinationNow(currentDestination, requiredDestinationId)) {
//            val args = ChatFragment.newInstance(currentUserId,user.id)
//            findNavController().navigate(R.id.action_userFragment_to_chatFragment, args.arguments)
//        }
//
//
//    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        observeViewModel()


    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        MenuInflater(activity).inflate(R.menu.main_menu, menu)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.item_logout) {
            viewModel.logout()
        }
        if (item.itemId == R.id.item_status_curier) {
            goToStatusUser()
//
        }

        return super.onOptionsItemSelected(item)

    }

    override fun onResume() {
        super.onResume()
        viewModel.setUserOnLine(true)

    }

    override fun onPause() {
        super.onPause()
        viewModel.setUserOnLine(false)

    }

    private fun observeLiveData() {
        viewModel1.modelItem.observe(viewLifecycleOwner) { item ->
            item?.let { items ->
                items.data?.forEach { item ->
                    println("id: ${item.id}")
                    Log.d("itemCoin", "id: ${item.id}")
                    typeOrders.add(item.typeorder)
                    println("type: ${item.typeorder}")
                    Log.d("itemCoin", "type: ${item.typeorder}")


                }
                Log.d("itemCoin", "All: ${typeOrders[0]}")
                Log.d("itemCoin", "All: ${typeOrders[1]}")
                Log.d("itemCoin", "All: ${typeOrders[2]}")
                Log.d("itemCoin", "All: ${typeOrders[3]}")
                Log.d("itemCoin", "All: ${typeOrders[4]}")
                Log.d("itemCoin", "All: ${typeOrders[5]}")
                Log.d("itemCoin", "All: ${typeOrders[6]}")
            }
        }


    }

    companion object {

        @JvmStatic
        fun newInstance(currentUserId: String) =
            UserFragment().apply {
                arguments = Bundle().apply {
                    putString(CUR_ID, currentUserId)
                }
            }
    }
}