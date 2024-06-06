package com.ka.courierka.coin.repo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ka.courierka.coin.Typeorder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TypeViewModel (
    private val repository: Repository
) : ViewModel() {

    val modelItem = MutableLiveData<Resource<List<Typeorder>>>()

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