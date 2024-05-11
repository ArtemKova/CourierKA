package com.ka.courierka.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.ka.courierka.courier.User

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

    fun createOrder(order: Order) {
        ordersRefernce
            .push()
            .setValue(order) 
            .addOnSuccessListener(object : OnSuccessListener<Void>{
                override fun onSuccess(p0: Void?) {
//                    TODO("Not yet implemented")
                }

            })


    }
}