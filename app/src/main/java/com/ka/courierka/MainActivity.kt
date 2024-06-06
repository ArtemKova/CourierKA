package com.ka.courierka

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.ka.courierka.coin.repo.TypeViewModel
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.activityScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.scope.Scope


class MainActivity : AppCompatActivity(), AndroidScopeComponent {
    private lateinit var navController: NavController
    override val scope: Scope by activityScope()
    private val viewModel by viewModel<TypeViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.doNetworkCall()
        observeLiveData()

        navController = Navigation.findNavController(this, R.id.fragmentContainerView)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        navHostFragment.view?.visibility = View.INVISIBLE

        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.nav_graph)
        val destination = R.id.loginFragment
        graph.setStartDestination(destination)


        navHostFragment.navController.graph = graph
        navHostFragment.view?.visibility = View.VISIBLE
    }
    private fun observeLiveData(){
        viewModel.modelItem.observe(this) { item ->
            item?.let {items->
                items.data?.forEach {item->
                    println("id: ${item.id}")
                    Log.d("itemCoin","id: ${item.id}")

                    println("type: ${item.typeorder}")
                    Log.d("itemCoin","type: ${item.typeorder}")

                }
            }
        }
    }
}