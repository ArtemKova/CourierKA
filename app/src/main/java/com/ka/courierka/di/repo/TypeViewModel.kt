package com.ka.courierka.di.repo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ka.courierka.di.TypeOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TypeViewModel @Inject constructor(
    private val repository: RepositoryImpl
) : ViewModel() {

    val modelItem = MutableLiveData<Resource<List<TypeOrder>>>()

    fun doNetworkCall(){
        CoroutineScope(Dispatchers.IO).launch {
            val resource = repository.doNetworkCall()
            withContext(Dispatchers.Main){
                resource.data?.let {
                    modelItem.value = resource
                }
            }
        }
    }
}