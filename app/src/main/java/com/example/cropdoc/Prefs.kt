package com.example.cropdoc

import android.content.Context
import android.content.SharedPreferences

class Prefs (context: Context) {
    val PREFS_NAME = "com.cropdoc.sharedpreferences"
    val SHARED_NAME = "shared_data"
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, 0)
    var name: String?
        get() = prefs.getString(SHARED_NAME, "def_val")
        set(value) = prefs.edit().putString(SHARED_NAME, value).apply()
}