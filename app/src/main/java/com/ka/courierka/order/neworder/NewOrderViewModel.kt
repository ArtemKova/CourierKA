package com.ka.courierka.order.neworder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.ka.courierka.order.Order

class NewOrderViewModel : ViewModel() {
    private var auth = FirebaseAuth.getInstance()
    val database = Firebase.database
    val ordersRefernce = database.getReference("Orders")
    private var error = MutableLiveData<String>()
    private var user = MutableLiveData<FirebaseUser>()
    fun getError(): LiveData<String> {
        return error
    }
    fun getUser(): LiveData<FirebaseUser> {
        auth.addAuthStateListener {
            if (it.currentUser != null) {
                user.value = it.currentUser
            }
        }
        return user
    }
    fun setOrderId(): String? {
        return ordersRefernce.push().key
    }
    fun createOrder(order: Order)   {
        ordersRefernce
            .child(order.id)
            .setValue(order)
            .addOnSuccessListener(object : OnSuccessListener<Void>{
                override fun onSuccess(p0: Void?) {
                }

            })
    }
}