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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UsersViewModel @Inject constructor (
    repository: OrderListRepositoryImpl
) :ViewModel() {

    private var auth = FirebaseAuth.getInstance()
    var user = MutableLiveData<FirebaseUser>()
    var users = MutableLiveData<MutableList<User>>()
    private var useres = MutableLiveData<User>()
    private var courier = MutableLiveData<Boolean>()
    private var orders = MutableLiveData<MutableList<Order>>()
    private val database = Firebase.database
    private val myRef = database.getReference("Users")
    private val myOrder = database.getReference("Orders")
    private val repository = repository
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
        var typeCourier = false
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var currentUser: FirebaseUser? = auth.currentUser ?: return
                var usersCourier = mutableListOf<User>()
                for (datasnapshot in snapshot.children) {
                    var value = datasnapshot.getValue<User>()
                    value?.let{
                        if (currentUser != null) {
                            if (it.id == currentUser.uid) {
                                typeCourier = it.courier
                                usersCourier.add(it)
                            }
                        }
                    }
                }
                if (usersCourier.isEmpty()) {
                    logout()
                } else {
                    users.value = usersCourier
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
        courier.value = typeCourier
        return users
    }

    fun getCourierK(): Boolean {
        var typeCourier = false
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var currentUser: FirebaseUser? = auth.currentUser ?: return
                var users1 = mutableListOf<User>()
                for (datasnapshot in snapshot.children) {
                    var value = datasnapshot.getValue<User>()
                    value?.let{
                        if (currentUser != null) {
                            if (it.id == currentUser.uid) {
                                typeCourier = it.courier
                                users1.add(it)

                            }
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        return typeCourier
    }


    fun getUsers(): LiveData<MutableList<User>> {
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var currentUser: FirebaseUser? = auth.currentUser ?: return
                var users1 = mutableListOf<User>()
                var typeCourier = false
                for (datasnapshot in snapshot.children) {
                    var value = datasnapshot.getValue<User>()
                        value?.let{
                        if (currentUser != null) {
                            if (it.id.equals(currentUser.uid)) {
                                typeCourier = it.courier
                            }
                        }
                    }
                }
                for (datasnapshot in snapshot.children) {
                    var value = datasnapshot.getValue<User>()
                    value?.let{
                        if (currentUser != null) {
                            if (!it.id.equals(currentUser.uid) && it.courier != typeCourier) {
                                users1.add(it)
                            }
                        }
                    }
                }
                users.value = users1
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
        return users
    }

    fun clear() {
        viewModelScope.launch { repository.clear() }
    }

    fun getOrders(){
        myOrder.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var users = mutableListOf<Order>()
                for (datasnapshot in snapshot.children) {
                    var value = datasnapshot.getValue<Order>()
                    value?.let { users.add(it)
                        viewModelScope.launch {
                            addOrderItemUseCase.addOrderItem(it)
                        }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
//
    }

    fun getOrderes(): LiveData<List<Order>> {

        var orderes = getOrderListUseCase.getOrderList()
            return orderes
    }


    fun setUserOnLine(online: Boolean) {
        var firebaseUser = auth.currentUser
        if (firebaseUser == null) {
            return
        }
        myRef.child(firebaseUser.uid).child("onLine").setValue(online)

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