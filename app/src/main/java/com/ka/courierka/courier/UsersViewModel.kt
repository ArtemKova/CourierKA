package com.ka.courierka.courier

import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.Firebase
import com.example.data.data.Order
import com.example.data.domain.AddOrderItemUseCase
import com.example.data.domain.DeleteOrderItemUseCase
import com.example.data.domain.EditOrderItemUseCase
import com.example.data.domain.GetOrderItemUseCase
import com.example.data.domain.GetOrderListUseCase
import com.example.data.domain.OrderListRepository
import com.google.firebase.database.database
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UsersViewModel @Inject constructor (
    repository: OrderListRepository
) :ViewModel() {

    private var auth = FirebaseAuth.getInstance()
    var user = MutableLiveData<FirebaseUser>()
    var users = MutableLiveData<MutableList<User>>()
    private var courier = MutableLiveData<Boolean>()
    private var orders = MutableLiveData<MutableList<Order>>()
    private val database = Firebase.database
    private val myRef = database.getReference("Users")
    private val myOrder = database.getReference("Orders")
    private val getOrderListUseCase = GetOrderListUseCase(repository)
    private val deleteOrderItemUseCase = DeleteOrderItemUseCase(repository)
    private val addOrderItemUseCase = AddOrderItemUseCase(repository)
    private val editOrderItemUseCase = EditOrderItemUseCase(repository)
    private val getOrderItemUseCase = GetOrderItemUseCase(repository)


    init {
        getUser()
        getCourier()
        getOrders()
    }
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
                val currentUser: FirebaseUser? = auth.currentUser ?: return
                val usersCourier = mutableListOf<User>()
                for (datasnapshot in snapshot.children) {
                    val value = datasnapshot.getValue<User>()
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
    }

    fun getOrderes(): LiveData<List<Order>> {
        var orders = getOrderListUseCase.getOrderList()
        return orders
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