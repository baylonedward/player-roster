package com.baylonedward.player_roster.utilities.ui

/**
 * Created by: ebaylon.
 * Created on: 09/11/2021.
 */
interface BasicListItemInterface {
    fun getCount(): Int
    fun getTitle(position: Int): String
    fun getOnClick(position: Int): () -> Unit
}