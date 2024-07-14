package com.ka.courierka

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.ka.courierka.di.TypViewModel
import com.ka.courierka.di.TypeOrder


import dagger.hilt.android.AndroidEntryPoint



import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class MainActivity : AppCompatActivity()
//    , AndroidScopeComponent
    {
    @Named("TypeStringFirst")
    @Inject
    lateinit var typeOrder: TypeOrder

    private val viewModel1: TypViewModel by viewModels()
//    private lateinit var navController: NavController
//    override val scope: Scope by activityScope()
//    private val viewModel by viewModel<TypeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (::typeOrder.isInitialized) {
            Log.d(
                "CarViewModelLog",
                "Simple car from ViewModel:- ${typeOrder.id}   ${typeOrder.typeorder}"
            )
        }
//        viewModel.doNetworkCall()
//        observeLiveData()
        viewModel1

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        navHostFragment.view?.visibility = View.INVISIBLE

        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.nav_graph)
        val destination = R.id.loginFragment
        graph.setStartDestination(destination)


       navController.graph = graph
        navHostFragment.view?.visibility = View.VISIBLE
    }
//    private fun observeLiveData(){
//        viewModel.modelItem.observe(this) { item ->
//            item?.let {items->
//                items.data?.forEach {item->
//                    println("id: ${item.id}")
//                    Log.d("itemCoin","id: ${item.id}")
//
//                    println("type: ${item.typeorder}")
//                    Log.d("itemCoin","type: ${item.typeorder}")
//
//                }
//            }
//        }
//    }
}