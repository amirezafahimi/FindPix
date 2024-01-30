package com.example.findpix.util

import android.content.Context
import android.net.ConnectivityManager


object NetworkUtils {
    // Method to check if the device is connected to the internet
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
        return false
    }
}