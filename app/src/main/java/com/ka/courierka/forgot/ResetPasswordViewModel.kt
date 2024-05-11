package com.ka.courierka.forgot

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class ResetPasswordViewModel : ViewModel() {

    var auth = FirebaseAuth.getInstance()

    private  var error = MutableLiveData<String>()
    private  var success = MutableLiveData<Boolean>()

    fun ResetPassword(email:String){
        auth.sendPasswordResetEmail(email).addOnSuccessListener {
            success.value = true

        }.addOnFailureListener {
            error.value = it.message
        }
    }
    fun getError(): LiveData<String> {
        return error
    }
    fun isSuccess(): LiveData<Boolean> {
        return success
    }
}