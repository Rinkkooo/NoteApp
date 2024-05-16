package com.example.noteapp.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.noteapp.R
import com.example.noteapp.databinding.ActivityMainBinding
import com.example.noteapp.utils.PreferenceHelper

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var preferenceHelper: PreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferenceHelper = PreferenceHelper()
        preferenceHelper.unit(this)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        if (preferenceHelper.isOnBoardShow) {
            navController.navigate(R.id.onBoardFragment)
            preferenceHelper.isOnBoardShow = true
        }else{
            navController.navigate(R.id.noteFragment)
        }
    }

}