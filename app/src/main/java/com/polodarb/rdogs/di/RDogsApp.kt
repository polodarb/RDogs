package com.polodarb.rdogs.di

import android.app.Application
import com.google.android.material.color.DynamicColors
import dagger.Module
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RDogsApp : Application() {

    override fun onCreate() {
        DynamicColors.applyToActivitiesIfAvailable(this)
        super.onCreate()
    }

}