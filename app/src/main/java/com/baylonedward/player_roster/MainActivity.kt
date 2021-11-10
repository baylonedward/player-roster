package com.baylonedward.player_roster

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.baylonedward.player_roster.databinding.ActivityMainBinding
import com.baylonedward.player_roster.features.team.add.AddTeamDialogFragment
import com.baylonedward.player_roster.navigation.NavigationCoordinator
import com.baylonedward.player_roster.navigation.NavigationViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.system.exitProcess

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    // navigation view model
    private val navigationViewModel by viewModels<NavigationViewModel>()

    // view binding
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // navigation controller
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        // set bottom navigation
        val navView: BottomNavigationView = binding.navView
        navView.setupWithNavController(navController)

        // observe bottom navigation visibility state
        navigationViewModel.showBottomNavigation.observe(this) {
            if (it == null) return@observe

            navView.visibility = if (it) View.VISIBLE else View.GONE
        }

        // observe navigation
        navigationViewModel.navigation.observe(this) { coordinator ->
            if (coordinator == null) return@observe
            when (coordinator) {
                is NavigationCoordinator.ExitApp -> exitProcess(0)
                is NavigationCoordinator.Restart -> restartApp()
                is NavigationCoordinator.NavigateUp -> navController.navigateUp()
                is NavigationCoordinator.PopBack -> navController.popBackStack()
                is NavigationCoordinator.PopBackTo -> navController.popBackStack(
                    coordinator.destinationId,
                    coordinator.inclusive
                )
                is NavigationCoordinator.DialogScreen -> {
                    coordinator.dialogFragment?.also {
                        it.show()
                    }
                }
                else -> {
                    // for fragments
                    coordinator.destination?.also {
                        navController.navigate(it, coordinator.argsBundle, coordinator.navOptions)
                    }
                }
            }
        }
    }

    private fun restartApp() {
        viewModelStore.clear()
        finishAndRemoveTask()
        startActivity(Intent(this, MainActivity::class.java))
    }
}