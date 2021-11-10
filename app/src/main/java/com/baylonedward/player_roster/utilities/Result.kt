package com.baylonedward.player_roster.utilities

/**
 * Created by: ebaylon.
 * Created on: 10/11/2021.
 */
class AppResult<out T>(private val value: T?, private val failed: Boolean = false) {

    fun isSuccess() = failed

    fun getValue() = this.value

    companion object {
        fun <T> success(value: T): AppResult<T> {
            return AppResult(value)
        }

        fun <T> failed(message: T): AppResult<T> {
            return AppResult(message, failed = true)
        }
    }
}