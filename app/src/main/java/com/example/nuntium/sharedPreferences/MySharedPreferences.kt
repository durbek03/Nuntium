package com.example.nuntium.sharedPreferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object MySharedPreferences {
    val name = "mySharedPreferences"
    var sharedPreferences: SharedPreferences? = null
    val launchKey = "0"

    fun init(context: Context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)
        }
    }

    var isFirstLaunch: Boolean
        get() {
            val mode = sharedPreferences?.getBoolean(launchKey, true)
            return mode ?: true
        }
        set(value) {
            sharedPreferences?.edit {
                putBoolean(launchKey, value)
                commit()
            }
        }
}