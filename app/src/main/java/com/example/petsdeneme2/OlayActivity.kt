package com.example.petsdeneme2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.petsdeneme2.databinding.ActivityOlayBinding

class OlayActivity : AppCompatActivity() {

    private lateinit var tasarim : ActivityOlayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tasarim = ActivityOlayBinding.inflate(layoutInflater)
        val view = tasarim.root
        setContentView(view)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        NavigationUI.setupWithNavController(tasarim.bottomNav, navHostFragment.navController)

    }
}