package com.kadirdogan97.rickandmortyapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.kadirdogan97.rickandmortyapp.R
import com.kadirdogan97.rickandmortyapp.helper.observeNonNull
import com.kadirdogan97.rickandmortyapp.helper.runIfNull
import com.kadirdogan97.rickandmortyapp.viewmodel.VMMain
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel:VMMain by viewModel()

    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onSupportNavigateUp() = navController.navigateUp()

}