package com.baylonedward.player_roster.navigation

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.navigation.NavOptions
import com.baylonedward.player_roster.R
import com.baylonedward.player_roster.data.local.room.entity.player.Player
import com.baylonedward.player_roster.data.local.room.entity.team.TeamAndPlayers
import com.baylonedward.player_roster.features.player.add.AddPlayerDialogFragment
import com.baylonedward.player_roster.features.player.info.PlayerInfoFragmentArgs
import com.baylonedward.player_roster.features.team.add.AddTeamDialogFragment
import com.baylonedward.player_roster.features.team.info.TeamInfoFragmentArgs
import com.baylonedward.player_roster.utilities.SingleLiveData
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Created by: ebaylon.
 * Created on: 14/08/2021.
 */
@ExperimentalCoroutinesApi
class NavigationViewModel : ViewModel() {
    // navigation
    private val _navigation = SingleLiveData<NavigationCoordinator>(null)
    val navigation = _navigation.toLiveData()

    // bottom navigation visibility
    private val _showBottomNavigation = SingleLiveData<Boolean>(null)
    val showBottomNavigation = _showBottomNavigation.toLiveData()

    fun showBottomNavigation(boolean: Boolean = true) {
        _showBottomNavigation.value = boolean
    }

    fun exitApp() {
        _navigation.value = NavigationCoordinator.ExitApp
    }

    fun restartApp() {
        _navigation.value = NavigationCoordinator.Restart
    }

    fun popBackScreen() {
        _navigation.value = NavigationCoordinator.PopBack
    }

    fun navigateUp() {
        _navigation.value = NavigationCoordinator.NavigateUp
    }

    fun popBackToScreen(@IdRes destination: Int, inclusive: Boolean = false) {
        _navigation.value = NavigationCoordinator.PopBackTo(destination, inclusive)
    }

    fun addTeamScreen(fragmentManager: FragmentManager) {
        val dialogFragment = AddTeamDialogFragment(fragmentManager)
        _navigation.value =
            NavigationCoordinator.DialogFragmentScreen(_dialogFragment = dialogFragment)
    }

    fun addPlayerScreen(fragmentManager: FragmentManager) {
        val dialogFragment = AddPlayerDialogFragment(fragmentManager)
        _navigation.value =
            NavigationCoordinator.DialogFragmentScreen(_dialogFragment = dialogFragment)
    }

    fun playerInfoScreen(
        navOptions: NavOptions = NavigationCoordinator.slideFromRightNavOption(),
        player: Player
    ) {
        val destination = R.id.navigation_player_info
        val bundle = PlayerInfoFragmentArgs(player).toBundle()
        _navigation.value = NavigationCoordinator.FragmentScreen(
            navOptions = navOptions,
            navIdRes = destination,
            bundle = bundle
        )
    }

    fun teamInfoScreen(
        navOptions: NavOptions = NavigationCoordinator.slideFromRightNavOption(),
        teamAndPlayers: TeamAndPlayers
    ) {
        val destination = R.id.navigation_team_info
        val bundle = TeamInfoFragmentArgs(teamAndPlayers).toBundle()
        _navigation.value = NavigationCoordinator.FragmentScreen(
            navOptions = navOptions,
            navIdRes = destination,
            bundle = bundle
        )
    }
}