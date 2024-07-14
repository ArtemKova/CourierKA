package com.ka.courierka.order.order

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.ka.courierka.courier.User
import com.example.data.data.AppDataBase
import com.example.data.data.OrderListMapper
import com.example.data.data.OrderListRepositoryImpl
import com.example.data.domain.AddOrderItemUseCase
import com.example.data.domain.DeleteOrderItemUseCase
import com.example.data.domain.EditOrderItemUseCase
import com.example.data.domain.GetOrderItemUseCase
import com.example.data.domain.GetOrderListUseCase
import com.example.data.data.Order
import kotlinx.coroutines.launch

class OrderViewModel(application: Application) : AndroidViewModel(application) {

    private var auth = FirebaseAuth.getInstance()
    private var user = MutableLiveData<FirebaseUser>()
    private var users = MutableLiveData<MutableList<User>>()
    private var useres = MutableLiveData<User>()
    private var courier = MutableLiveData<Boolean>()
    private var orders = MutableLiveData<MutableList<Order>>()
    private val database = Firebase.database
    private val myRef = database.getReference("Users")
    private val myOrder = database.getReference("Orders")
    private val orderListDao = AppDataBase.getInstance(application).orderListDao()
    private val mapper = OrderListMapper()
    private val repository = OrderListRepositoryImpl(application)
    private val getOrderListUseCase = GetOrderListUseCase(repository)
    private val deleteOrderItemUseCase = DeleteOrderItemUseCase(repository)
    private val addOrderItemUseCase = AddOrderItemUseCase(repository)
    private val editOrderItemUseCase = EditOrderItemUseCase(repository)
    private val getOrderItemUseCase = GetOrderItemUseCase(repository)

    fun getOrders(id: String): LiveData<MutableList<Order>> {

        myOrder.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
//                var currentUser: FirebaseUser? = auth.currentUser ?: return
                var users1 = mutableListOf<Order>()
//                var tC = false

//                for (datasnapshot in snapshot.children) {
//                    var value = datasnapshot.getValue<Order>()
//                    if (value != null) {
//                        if (currentUser != null) {
//                            if (value.id.equals(currentUser.uid)) {
//                                tC = value.courier
//                            }
//                        }
//                    }
//
//                }

                for (datasnapshot in snapshot.children) {
                    var value = datasnapshot.getValue<Order>()
                    if (value != null) {
//                        if (currentUser != null) {999+999999999++66689+9+
//                            if (!value.id.equals(currentUser.uid)&&value.courier!=tC) {
                        users1.add(value)
                        viewModelScope.launch {
                            addOrderItemUseCase.addOrderItem(value)
                        }
//                        orderListDao.addOrderItem(mapper.mapOrderToDBOrder(value))
//                            }
//                        }
                    }

                }
                orders.value = users1
                var orderes = getOrderListUseCase.getOrderList()

//                orders = MediatorLiveData<List<Order>>().apply {
//                    addSource(orderListDao.getOrderList()){
//
//                    }
//                }
            }

            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
            }
        })



        return orders
    }

    fun setGetOrder(order: String, courier : String): String? {
        var firebaseUser = auth.currentUser
        if (firebaseUser == null) {
            return ""
        }
        val id = auth.currentUser?.let { myRef.child(it.uid).toString() }

        myOrder.child(order).child("courier").setValue(id)
        return id
    }

    fun getUsers(id: String): User? {
        var user: User? = null

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var currentUser: FirebaseUser? = auth.currentUser ?: return
                var users1 = mutableListOf<User>()
                var tC = false

                for (datasnapshot in snapshot.children) {
                    var value = datasnapshot.getValue<User>()
                    if (value != null) {
                        if (currentUser != null) {
                            if (value.id.equals(currentUser.uid)) {
                                tC = value.courier
                            }
                        }
                    }

                }
                for (datasnapshot in snapshot.children) {
                    var value = datasnapshot.getValue<User>()
                    if (value != null) {
                        if (currentUser != null) {
                            if (!value.id.equals(currentUser.uid) && value.courier != tC) {
                                users1.add(value)
                            }
                            if (id == value.id) {
                                user = value
                            }
                        }

                    }

                }
                users.value = users1
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })



        return user
    }
}