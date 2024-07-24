package com.ka.courierka.courier

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.example.data.data.AppDataBase
import com.example.data.data.Order
import com.example.data.data.OrderListMapper
import com.example.data.data.OrderListRepositoryImpl
import com.example.data.domain.AddOrderItemUseCase
import com.example.data.domain.DeleteOrderItemUseCase
import com.example.data.domain.EditOrderItemUseCase
import com.example.data.domain.GetOrderItemUseCase
import com.example.data.domain.GetOrderListUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class UsersViewModel(
    application: Application
) : AndroidViewModel(application) {

    private var auth = FirebaseAuth.getInstance()
    private var user = MutableLiveData<FirebaseUser>()
    private var users = MutableLiveData<MutableList<User>>()
    private var useres = MutableLiveData<User>()
    private var courier = MutableLiveData<Boolean>()
    private var orders = MutableLiveData<MutableList<Order>>()
    private val database = Firebase.database
    private val myRef = database.getReference("Users")
    private val myOrder = database.getReference("Orders")
    private val userDb =  AppDataBase.getInstance(application)
    private val orderListDao = userDb.orderListDao()
    private val mapper = OrderListMapper()
    private val repository = OrderListRepositoryImpl(application)
    private val getOrderListUseCase = GetOrderListUseCase(repository)
    private val deleteOrderItemUseCase = DeleteOrderItemUseCase(repository)
    private val addOrderItemUseCase = AddOrderItemUseCase(repository)
    private val editOrderItemUseCase = EditOrderItemUseCase(repository)
    private val getOrderItemUseCase = GetOrderItemUseCase(repository)



    fun getUser(): LiveData<FirebaseUser> {
        auth.addAuthStateListener {
            user.value = it.currentUser
        }
        return user
    }

    fun getCourier(): LiveData<MutableList<User>> {
        var tC = false
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var currentUser: FirebaseUser? = auth.currentUser ?: return
                var users1 = mutableListOf<User>()


                for (datasnapshot in snapshot.children) {
                    var value = datasnapshot.getValue<User>()
                    if (value != null) {
                        if (currentUser != null) {
                            if (value.id == currentUser.uid) {
                                tC = value.courier
                                users1.add(value)


                            }
                        }
                    }

                }
                if (users1.isEmpty()) {
                    logout()
                } else {

                    users.value = users1
                }

            }

            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
            }
        })
        courier.value = tC

        return users
    }

    fun getCourierK(): Boolean {
        var tC = false
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var currentUser: FirebaseUser? = auth.currentUser ?: return
                var users1 = mutableListOf<User>()


                for (datasnapshot in snapshot.children) {
                    var value = datasnapshot.getValue<User>()
                    if (value != null) {
                        if (currentUser != null) {
                            if (value.id == currentUser.uid) {
                                tC = value.courier
                                users1.add(value)

                            }
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
            }
        })

        return tC
    }


    fun getUsers(): LiveData<MutableList<User>> {

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
                        }
                    }

                }
                users.value = users1
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return users
    }
    fun getOrders(): LiveData<MutableList<Order>> {
        viewModelScope.launch {  repository.clear() }


        myOrder.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var users1 = mutableListOf<Order>()
                for (datasnapshot in snapshot.children) {
                    var value = datasnapshot.getValue<Order>()
                    if (value != null) {
                        users1.add(value)
                        Log.d("Look_id5", "${value.id}")
                        viewModelScope.launch {
                            addOrderItemUseCase.addOrderItem(value)
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

//                TODO("Not yet implemented")
            }
        })
        var orderes = getOrderListUseCase.getOrderList()
        orders = orderes as MutableLiveData<MutableList<Order>>

        return orders
    }

    fun getOrderes(): MutableLiveData<MutableList<Order>> {

        viewModelScope.launch {
            orders = getOrderListUseCase.getOrderList() as MutableLiveData<MutableList<Order>>
        }

        return orders
    }


    fun setUserOnLine(online: Boolean) {
        var firebaseUser = auth.currentUser
        if (firebaseUser == null) {
            return
        }
        myRef.child(firebaseUser.uid).child("onLine").setValue(online)

    }

    fun setGetOrder(order: Order) {

    }

    fun logout() {
        setUserOnLine(false)
        auth.signOut()
    }

    fun setChangeUserData(
        id: String,
        name: String,
        lastName: String,
        age: Int,
        city: String,
        typeCourier: Boolean
    ) {

        myRef.child(id).child("name").setValue(name)
        myRef.child(id).child("lastName").setValue(lastName)
        myRef.child(id).child("age").setValue(age)
        myRef.child(id).child("city").setValue(city)
        myRef.child(id).child("courier").setValue(typeCourier)

    }


}