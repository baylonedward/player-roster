package com.baylonedward.player_roster.data.local.enums

/**
 * Created by: ebaylon.
 * Created on: 10/11/2021.
 */
enum class Gender(val title: String) {
    MALE("Male"),
    FEMALE("Female");

    override fun toString(): String {
        return title
    }
}