package com.ka.courierka.geolocation

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.Arrays

const val REQUEST_CODE_ASK_PERMISSIONS = 1
class Geolocation(context: Context) {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val REQUIRED_SDK_PERMISSIONS = arrayListOf(Manifest.permission.ACCESS_FINE_LOCATION)
    val context = context
    fun geo(){
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val missingPermissions = ArrayList<String>()
            if (Build.VERSION.SDK_INT >= 28) {
                REQUIRED_SDK_PERMISSIONS.plus(Manifest.permission.FOREGROUND_SERVICE)
            }
            for (permission in REQUIRED_SDK_PERMISSIONS) {
                val result = ContextCompat.checkSelfPermission(context, permission)
                if (result != PackageManager.PERMISSION_GRANTED) {
                    missingPermissions.add(permission)
                }
            }
            if (missingPermissions.isNotEmpty()) {
                val permission = missingPermissions
                    .toTypedArray()
                ActivityCompat.requestPermissions(context as Activity, permission, REQUEST_CODE_ASK_PERMISSIONS)
            } else {
                val grantResults = IntArray(REQUIRED_SDK_PERMISSIONS.size)
                Arrays.fill(grantResults, PackageManager.PERMISSION_GRANTED)
            }
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->

            }
    }
}