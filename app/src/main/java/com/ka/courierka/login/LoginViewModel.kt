package com.ka.courierka.login


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginViewModel(
    var auth: FirebaseAuth = FirebaseAuth.getInstance()
) : ViewModel() {
    private var error = MutableLiveData<String>()
    private var user = MutableLiveData<FirebaseUser>()
    var userid: LiveData<FirebaseUser> = user
    fun getError(): LiveData<String> {
        return error
    }

    fun getUser(): MutableLiveData<FirebaseUser> {
        auth.addAuthStateListener {
            FirebaseAuth.AuthStateListener {
                if (it.currentUser != null) {
                    user.value = it.currentUser
                }
            }
        }
        return user
    }

    fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            user.value = it.user
        }.addOnFailureListener {
            error.value = it.message
        }
    }
}
