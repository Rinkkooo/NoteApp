package com.example.noteapp.utils

import android.content.Context
import android.content.SharedPreferences

class PreferenceHelper {
    private lateinit var sharedPreference: SharedPreferences

    fun unit(context: Context){
        sharedPreference = context.getSharedPreferences("shared", Context.MODE_PRIVATE)
    }

    var isOnBoardShow: Boolean
        get() = sharedPreference.getBoolean("board", false)
        set(value) = sharedPreference.edit().putBoolean("board", value).apply()
}