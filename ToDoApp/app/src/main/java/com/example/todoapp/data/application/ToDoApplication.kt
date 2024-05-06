package com.example.todoapp.data.application

import android.app.Application
import com.zoho.catalyst.setup.ZCatalystApp
import com.zoho.catalyst.setup.ZCatalystSDKConfigs
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ToDoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ZCatalystApp.init(
            context = applicationContext,
            environment = ZCatalystSDKConfigs.Environment.DEVELOPMENT
        )
    }
}