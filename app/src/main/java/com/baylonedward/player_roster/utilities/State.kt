package com.baylonedward.player_roster.utilities

import androidx.annotation.Keep

/**
 * Created by: ebaylon.
 * Created on: 16/11/2020.
 *
 * Helper class to wrap Object easily with States: Success, Error, Loading, Failed
 */
@Keep
sealed class State<out T> {
  abstract val message: String?

  data class Success<T>(val value: T?, override val message: String? = null) : State<T>()
  data class Error(override val message: String) : State<Nothing>()
  data class Loading(override val message: String? = null) : State<Nothing>()
  data class Failed(override val message: String) : State<Nothing>()
}