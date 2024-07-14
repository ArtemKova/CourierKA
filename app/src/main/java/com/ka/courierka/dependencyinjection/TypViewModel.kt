package com.ka.courierka.dependencyinjection

import android.util.Log
import androidx.lifecycle.ViewModel

import dagger.hilt.android.lifecycle.HiltViewModel


import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class TypViewModel @Inject constructor(
    @Named("TypeStringSecond")  typeOrder: TypeOrder
): ViewModel(){
   init  {
       Log.d("CarViewModelLog", "Simple car from ViewModel:- ${typeOrder.id}   ${typeOrder.typeorder}")
    }

}