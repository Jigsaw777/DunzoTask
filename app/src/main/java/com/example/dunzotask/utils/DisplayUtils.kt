package com.example.dunzotask.utils

import android.content.Context
import com.example.dunzotask.R

object DisplayUtils {
    fun calculateNumberOfColumns(context: Context):Int{
        val displayMetrics=context.resources.displayMetrics
        val screenWidth=displayMetrics.widthPixels/displayMetrics.density
        return (screenWidth/context.resources.getInteger(R.integer.columnWidth)).toInt()
    }
}