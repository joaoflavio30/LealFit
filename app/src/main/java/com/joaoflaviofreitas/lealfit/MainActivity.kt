package com.joaoflaviofreitas.lealfit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.FirebaseAuth
import com.joaoflaviofreitas.lealfit.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    @Inject
    lateinit var auth: FirebaseAuth

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHostFragment.navController
        setupNav(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun setupNav(navController: NavController) {
        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)

        if (auth.currentUser != null) {
           navGraph.setStartDestination(R.id.workoutFragment)
            navController.setGraph(navGraph, null)
        } else {
            navGraph.setStartDestination(R.id.welcomeFragment)
            navController.setGraph(navGraph, null)
        }
    }

}