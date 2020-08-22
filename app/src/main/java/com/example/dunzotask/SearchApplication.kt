package com.example.dunzotask

import android.app.Application
import com.example.dunzotask.utils.ObjectBox
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SearchApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        ObjectBox.init(this)
    }
}