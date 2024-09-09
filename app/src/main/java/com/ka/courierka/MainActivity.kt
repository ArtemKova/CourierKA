package com.ka.courierka

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.ka.courierka.di.TypeOrder
import com.ka.courierka.geolocation.Geolocation
import com.ka.courierka.navigation.Navigation


import dagger.hilt.android.AndroidEntryPoint
import java.util.Arrays


import javax.inject.Inject
import javax.inject.Named


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Geolocation(this).geo()
        setContent {
            val navController = rememberNavController()
            Navigation(navController)
        }


    }

}