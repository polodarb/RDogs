package com.polodarb.rdogs.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.HapticFeedbackConstants
import android.view.View
import com.polodarb.rdogs.data.local.Breed
import java.util.Locale

object Utils {

    fun listConverter(list: Map<String, List<String>>): List<Breed> {
        val convertList = list.flatMap { e ->
            e.value.map { it ->
                "${e.key.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }} $it"
            }.ifEmpty {
                listOf(e.key.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
                })
            }
        }

        val listBreed = arrayListOf<Breed>()

        convertList.map {
            listBreed.add(Breed(0, it))
        }

        return listBreed
    }

    fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }

                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }

                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }

    fun setHapticEffect(v: View) {
        v.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_RELEASE)
    }

}