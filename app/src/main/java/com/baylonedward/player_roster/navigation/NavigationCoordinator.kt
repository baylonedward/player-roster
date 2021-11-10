package com.baylonedward.player_roster.navigation

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavOptions
import com.baylonedward.player_roster.R
import com.baylonedward.player_roster.data.local.room.entity.player.Player
import com.baylonedward.player_roster.features.player.info.PlayerInfoFragmentArgs
import com.baylonedward.player_roster.utilities.base.CustomDialogFragment

/**
 * Created by: ebaylon.
 * Created on: 11/11/2021.
 */
sealed class NavigationCoordinator(
    @IdRes val destination: Int?,
    val argsBundle: Bundle? = null,
    val dialogFragment: CustomDialogFragment? = null
) {
    abstract val navOptions: NavOptions?

    object ExitApp : NavigationCoordinator(destination = null) {
        override val navOptions: NavOptions?
            get() = null
    }

    object PopBack : NavigationCoordinator(destination = null) {
        override val navOptions: NavOptions?
            get() = null
    }

    object NavigateUp : NavigationCoordinator(destination = null) {
        override val navOptions: NavOptions?
            get() = null
    }

    object Restart : NavigationCoordinator(destination = null) {
        override val navOptions: NavOptions?
            get() = null
    }

    data class PopBackTo(val destinationId: Int, val inclusive: Boolean = false) :
        NavigationCoordinator(destination = null) {
        override val navOptions: NavOptions?
            get() = null
    }

    data class FragmentScreen(
        override val navOptions: NavOptions,
        @IdRes val navIdRes: Int,
        val bundle: Bundle?
    ) : NavigationCoordinator(
        destination = navIdRes,
        argsBundle = bundle
    )

    data class DialogFragmentScreen(
        override val navOptions: NavOptions? = null,
        private val _dialogFragment: CustomDialogFragment
    ) : NavigationCoordinator(
        destination = null,
        dialogFragment = _dialogFragment
    )

    companion object {
        fun slideFromRightNavOption(
            @IdRes popUpTo: Int? = null,
            popUpInclusive: Boolean = false,
            singleTop: Boolean = true
        ): NavOptions {
            val builder = NavOptions.Builder()
                .setEnterAnim(R.anim.slide_in_right)
                .setExitAnim(R.anim.slide_out_left)
                .setPopEnterAnim(R.anim.slide_in_left)
                .setPopExitAnim(R.anim.slide_out_right)
                .setLaunchSingleTop(singleTop)
            popUpTo?.also { builder.setPopUpTo(it, popUpInclusive) }
            return builder.build()
        }

        fun slideFromLeftNavOption(
            @IdRes popUpTo: Int? = null,
            popUpInclusive: Boolean = false,
            singleTop: Boolean = true
        ): NavOptions {
            val builder = NavOptions.Builder()
                .setEnterAnim(R.anim.slide_in_left)
                .setExitAnim(R.anim.slide_out_right)
                .setPopEnterAnim(R.anim.slide_in_right)
                .setPopExitAnim(R.anim.slide_out_left)
                .setLaunchSingleTop(singleTop)
            popUpTo?.also { builder.setPopUpTo(it, popUpInclusive) }
            return builder.build()
        }

        fun slideFromBottomNavOption(
            @IdRes popUpTo: Int? = null,
            popUpInclusive: Boolean = false,
            singleTop: Boolean = true
        ): NavOptions {
            val builder = NavOptions.Builder()
                .setEnterAnim(R.anim.slide_up)
                .setExitAnim(R.anim.fade_out)
                .setPopEnterAnim(R.anim.fade_in)
                .setPopExitAnim(R.anim.slide_down)
                .setLaunchSingleTop(singleTop)
            popUpTo?.also { builder.setPopUpTo(it, popUpInclusive) }
            return builder.build()
        }
    }
}