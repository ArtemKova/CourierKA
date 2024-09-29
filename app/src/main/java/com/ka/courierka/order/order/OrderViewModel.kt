package com.ka.courierka.order.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.Firebase
import com.ka.courierka.courier.User
import com.example.data.data.OrderListMapper
import com.example.data.data.OrderListRepositoryImpl
import com.example.data.domain.AddOrderItemUseCase
import com.example.data.domain.DeleteOrderItemUseCase
import com.example.data.domain.EditOrderItemUseCase
import com.example.data.domain.GetOrderItemUseCase
import com.example.data.domain.GetOrderListUseCase
import com.example.data.data.Order
import com.google.firebase.database.database
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject internal constructor(
    repository: OrderListRepositoryImpl
) : ViewModel() {
    private var auth = FirebaseAuth.getInstance()
    private var user = MutableLiveData<FirebaseUser>()
    private var users = MutableLiveData<MutableList<User>>()
    private var useres = MutableLiveData<User>()
    private var courier = MutableLiveData<Boolean>()
    private var orders = MutableLiveData<MutableList<Order>>()
    private val database = Firebase.database
    private val myRef = database.getReference("Users")
    private val myOrder = database.getReference("Orders")
    private val mapper = OrderListMapper()
    private val getOrderListUseCase = GetOrderListUseCase(repository)
    private val deleteOrderItemUseCase = DeleteOrderItemUseCase(repository)
    private val addOrderItemUseCase = AddOrderItemUseCase(repository)
    private val editOrderItemUseCase = EditOrderItemUseCase(repository)
    private val getOrderItemUseCase = GetOrderItemUseCase(repository)
    fun getOrders(): LiveData<MutableList<Order>> {

        var orderes = getOrderListUseCase.getOrderList()
        orders = orderes as MutableLiveData<MutableList<Order>>
        return orders
    }
    fun setGetOrder(order: String, courier: String): String? {
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
            }
        })
        return user
    }
}