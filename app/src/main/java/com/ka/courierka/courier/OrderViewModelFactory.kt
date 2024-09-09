package com.ka.courierka.courier

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class OrderViewModelFactory (val application: Application) :
    ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return UsersViewModel(application) as T
//    }
}