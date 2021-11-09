package com.baylonedward.player_roster.utilities.extensions

/**
 * Created by: ebaylon.
 * Created on: 09/11/2021.
 */

fun <T> ArrayList<T>.changeList(new: List<T>) {
    this.clear()
    this.addAll(new)
}