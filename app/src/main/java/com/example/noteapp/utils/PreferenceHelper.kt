package com.example.noteapp.utils

import android.content.Context
import android.content.SharedPreferences

class PreferenceHelper {

    private lateinit var sharedPreferences: SharedPreferences

    fun unit(context: Context) {
        sharedPreferences = context.getSharedPreferences("shared", Context.MODE_PRIVATE)
    }

    var isOnBoardShow: Boolean
        get() = sharedPreferences.getBoolean("board", false)
        set(value) = sharedPreferences.edit().putBoolean("board", value).apply()

    var isSignUpShow: Boolean
        get() = sharedPreferences.getBoolean("sign_up", false)
        set(value) = sharedPreferences.edit().putBoolean("sign_up", value).apply()
}
