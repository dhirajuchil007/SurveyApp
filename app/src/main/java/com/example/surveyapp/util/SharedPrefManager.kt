package com.example.surveyapp.util

import android.content.Context
import android.content.SharedPreferences

class SharedPrefManager(var context: Context) {
    var sharedpref: SharedPreferences

    init {
        sharedpref = context.getSharedPreferences("APP", Context.MODE_PRIVATE)
    }

    fun addEmail(email: String) {
        sharedpref.edit().putString("email", email).apply()
    }

    fun getEmail(): String {
        return sharedpref.getString("email", "") ?: ""
    }
}