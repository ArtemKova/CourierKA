package com.ka.courierka

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import com.ka.courierka.di.TypeOrder


import dagger.hilt.android.AndroidEntryPoint


import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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

}