package com.example.dunzotask.utils

import android.content.Context
import com.example.dunzotask.domain.entities.dbEntities.MyObjectBox
import io.objectbox.BoxStore

object ObjectBox {
    lateinit var boxStore: BoxStore
        private set

    fun init(context: Context) {
        boxStore = MyObjectBox.builder()
            .androidContext(context.applicationContext)
            .build()
    }
}