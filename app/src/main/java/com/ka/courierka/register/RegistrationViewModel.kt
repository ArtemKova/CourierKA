package com.ka.courierka.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.ka.courierka.courier.User

class RegistrationViewModel: ViewModel() {
    private  var auth = FirebaseAuth.getInstance()
    val database = Firebase.database
    val usersRefernce = database.getReference("Users")

    private  var error = MutableLiveData<String>()
    private  var user = MutableLiveData<FirebaseUser>()


    fun getError(): LiveData<String> {
        return error
    }

    fun getUser(): LiveData<FirebaseUser> {
        auth.addAuthStateListener {
            if (it.currentUser!= null){
                user.value = it.currentUser
            }
        }
        return user
    }

    fun signUp(
        email:String,
        password:String,
        name:String,
        lastName:String,
        age:Int,
        city:String,
        courier:Boolean
 ){
       auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
           var firebaseUser = it.user
           var user = firebaseUser?.let { it1 ->
               User(
                   it1.uid,
                   name,
                   lastName,
                   age,
                   false,
                   courier,
                   city
               )
           }
           if (user != null) {
               usersRefernce.child(user.id).setValue(user)
           }
       }.addOnFailureListener {
           error.value=it.message
       }
    }

}