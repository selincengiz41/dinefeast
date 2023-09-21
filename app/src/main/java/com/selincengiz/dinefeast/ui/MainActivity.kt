package com.selincengiz.dinefeast.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.firebase.auth.FirebaseAuth
import com.selincengiz.dinefeast.R
import com.selincengiz.dinefeast.common.Extensions.loadUrl
import com.selincengiz.dinefeast.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var auth: FirebaseAuth

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //Custom Toolbar variable
        binding.toolbar.mainActivityFunctions = this@MainActivity

        //bottomNav settings
        bottomNav()
    }

    fun bottomNav() {
        with(binding) {

            //Bottom Navigation Menu
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment

            NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment.navController)
            navHostFragment.navController.addOnDestinationChangedListener { controller, destination, _ ->

                when (destination.id) {

                    R.id.homeFragment -> {
                        toolbar.name = auth.currentUser?.displayName
                        visibilityBottomNav = true
                        visibilityToolbar = true


                    }

                    R.id.searchFragment -> {
                        toolbar.name = auth.currentUser?.displayName
                        visibilityBottomNav = true
                        visibilityToolbar = false


                    }

                    R.id.cartFragment -> {
                        toolbar.name = auth.currentUser?.displayName
                        visibilityBottomNav = true
                        visibilityToolbar = false


                    }

                    R.id.favoritesFragment -> {
                        toolbar.name = auth.currentUser?.displayName
                        visibilityBottomNav = true
                        visibilityToolbar = false

                    }

                    else -> {
                        visibilityBottomNav = false
                        visibilityToolbar = false


                    }

                }


            }
        }
    }

    fun logOut() {
        auth.signOut()
        binding.navHost.findNavController()
            .navigate(R.id.action_global_splashFragment)
    }
}