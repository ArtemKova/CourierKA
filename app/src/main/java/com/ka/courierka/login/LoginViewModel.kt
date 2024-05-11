package com.ka.courierka.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginViewModel(
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
):ViewModel() {


    private var error = MutableLiveData<String>()
    private var user = MutableLiveData<FirebaseUser>()





     fun getError(): LiveData<String>{

             return error

     }

    fun getUser():LiveData<FirebaseUser>{


        auth.addAuthStateListener {FirebaseAuth.AuthStateListener{

            if (it.currentUser!= null){
                Log.d("iduid1","${it.currentUser!!.uid}  " )
                user.value = it.currentUser
            }
        }


        }
        return user
    }





    fun login(email:String,password:String){

        auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
//            user.setValue(it.user)
            user.value = it.user
            Log.d("iduid1","${it.user?.uid} ")
        }.addOnFailureListener {
//            error.setValue(it.message)
            error.value = it.message

            Log.d("LOG_TAG", "${it.message}")
        }

    }

}