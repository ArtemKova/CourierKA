package com.ka.courierka

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.ka.courierka.di.TypeOrder


import dagger.hilt.android.AndroidEntryPoint
import java.util.Arrays


import javax.inject.Inject
import javax.inject.Named

const val REQUEST_CODE_ASK_PERMISSIONS = 1

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val REQUIRED_SDK_PERMISSIONS = arrayListOf(Manifest.permission.ACCESS_FINE_LOCATION)
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
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
           val missingPermissions =  ArrayList<String>()
            if (Build.VERSION.SDK_INT >= 28) {
                REQUIRED_SDK_PERMISSIONS.plus(Manifest.permission.FOREGROUND_SERVICE)
            }
            for (permission in REQUIRED_SDK_PERMISSIONS){
                val result = ContextCompat.checkSelfPermission(this, permission)
                if (result != PackageManager.PERMISSION_GRANTED){
                    missingPermissions.add(permission)
                }
            }
            if (missingPermissions.isNotEmpty()){
                val permission = missingPermissions
                    .toTypedArray()
                ActivityCompat.requestPermissions(this, permission, REQUEST_CODE_ASK_PERMISSIONS)
            } else {
                val grantResults = IntArray(REQUIRED_SDK_PERMISSIONS.size)
                Arrays.fill(grantResults, PackageManager.PERMISSION_GRANTED)
            }
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->

            }
    }

}