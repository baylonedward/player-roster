package com.baylonedward.player_roster

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by: ebaylon.
 * Created on: 09/11/2021.
 */
@HiltAndroidApp
class MainApp : Application() {

    companion object {
        const val sharedPreferenceName = "sharedPreference"
    }
}