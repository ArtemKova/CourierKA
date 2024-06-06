package com.ka.courierka.courier

import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.LinearLayout

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ka.courierka.R
import com.ka.courierka.coin.repo.TypeViewModel
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
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UserFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserFragment : Fragment(), AndroidScopeComponent {
    override val scope: Scope by fragmentScope()
    private val viewModel1 by viewModel<TypeViewModel>()
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var requiredDestinationId = 0
    private var typeOrders = arrayListOf<String>()
    private lateinit var recyclerViewUser: RecyclerView
    private lateinit var linearLayout: LinearLayout
    private lateinit var neworder: Button
    private lateinit var oldorders: Button
    private lateinit var usersAdapter: UsersAdapter
    private lateinit var currentUserId: String
    private lateinit var viewModel: UsersViewModel
    private  var deliv:Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            currentUserId = param1.toString()
        }
        viewModel = ViewModelProvider(this)[UsersViewModel::class.java]
        viewModel1.doNetworkCall()

        setHasOptionsMenu(true);
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_user, container, false)
        requiredDestinationId = R.id.userFragment
        linearLayout = view.findViewById(R.id.linearLayout)
        neworder = view.findViewById(R.id.neworder)
        oldorders = view.findViewById(R.id.oldorder)
        recyclerViewUser = view.findViewById(R.id.recyclerViewUsers)
        usersAdapter = UsersAdapter()
        recyclerViewUser.adapter = usersAdapter
        if (oldorders.text.equals("Old orders")){
            deliv = false
        } else {
            deliv = true
        }
        observeLiveData()
        // Inflate the layout for this fragment
        return view


    }

    fun initViews() {

        neworder.setOnClickListener() {
            goToNewOrder()
        }
        oldorders.setOnClickListener {
            if (oldorders.text.equals("Old orders")){
                oldorders.text = "Orders"
                deliv = true
            } else {
                oldorders.text = "Old orders"
                deliv = false
            }
        }
    }

    fun observeViewModel() {
        viewModel.getUser().observe(viewLifecycleOwner) {
            if (it == null) {
                goToLogin()
            }
        }
        viewModel.getOrders().observe(viewLifecycleOwner) {
            var orders = mutableListOf<Order>()
            for (order in it){
                if (deliv == order.delivered) {
                    orders.add(order)
                }
            }
            usersAdapter.setUsers(orders)
            usersAdapter.onUserClickListener = object : UsersAdapter.OnUserClickListener {
                override fun onUserClick(order: Order) {
                    Log.d("Look_id4","${order.id}")
                    goToOrder(order)
                }
            }
        }
        viewModel.getCourier().observe(viewLifecycleOwner){
            if (it.isEmpty()){
                viewModel.logout()
            }


            if (it[0].courier){
                linearLayout.visibility = GONE
            }
            else {
                linearLayout.visibility = VISIBLE
            }
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
            val args = NewOrderFragment.newInstance(currentUserId,typeOrders )
            findNavController().navigate(R.id.action_userFragment_to_newOrderFragment, args.arguments)
        }
    }
    private fun goToOrder(order:Order){
        val currentDestination = findNavController().currentDestination?.id
        if (isCorrectDestinationNow(currentDestination, requiredDestinationId)) {
            val args = OrderFragment.newInstance(
                 order.id,
                 order.name,
                 order.phone,
                 order.adress,
                 order.recadress,
                 order.customer_id,
                 order.time,
                 order.isPay,
                 order.courier,
                 order.city,
                 order.typeOrder,
                 order.delivered,
                currentUserId

                )
            findNavController().navigate(R.id.action_userFragment_to_orderFragment, args.arguments)
        }
    }

    private fun goToStatusUser() {
        val currentDestination = findNavController().currentDestination?.id
        if (isCorrectDestinationNow(currentDestination, requiredDestinationId)) {
            val args = StatusUser.newInstance(currentUserId,"")
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
        initViews()
        observeViewModel()


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
    private fun observeLiveData(){
        viewModel1.modelItem.observe(viewLifecycleOwner) { item ->
            item?.let {items->
                items.data?.forEach {item->
                    println("id: ${item.id}")
                    Log.d("itemCoin","id: ${item.id}")
                    typeOrders.add(item.typeorder)
                    println("type: ${item.typeorder}")
                    Log.d("itemCoin","type: ${item.typeorder}")



                }
                Log.d("itemCoin","All: ${typeOrders[0]}")
                Log.d("itemCoin","All: ${typeOrders[1]}")
                Log.d("itemCoin","All: ${typeOrders[2]}")
                Log.d("itemCoin","All: ${typeOrders[3]}")
                Log.d("itemCoin","All: ${typeOrders[4]}")
                Log.d("itemCoin","All: ${typeOrders[5]}")
                Log.d("itemCoin","All: ${typeOrders[6]}")
            }
        }


    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UserFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}