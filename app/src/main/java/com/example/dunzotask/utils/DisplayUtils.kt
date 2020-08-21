package com.example.dunzotask.utils

import android.content.Context
import android.content.res.Configuration
import com.example.dunzotask.R

object DisplayUtils {
    fun calculateNumberOfColumns(context: Context): Int {
        val displayMetrics = context.resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels / displayMetrics.density
        return (screenWidth / context.resources.getInteger(R.integer.columnWidth)).toInt()
    }

    fun isLandscape(context: Context): Boolean {
        return context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    }
}