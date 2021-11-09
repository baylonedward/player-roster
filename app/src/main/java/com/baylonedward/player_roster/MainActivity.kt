package com.baylonedward.player_roster

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.baylonedward.player_roster.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        // val appBarConfiguration = AppBarConfiguration(
        //    setOf(
        //        R.id.navigation_teams, R.id.navigation_players, R.id.navigation_trainings
        //    )
        // )
        // setupActionBarWithNavController(navController, appBarConfiguration)
    }
}