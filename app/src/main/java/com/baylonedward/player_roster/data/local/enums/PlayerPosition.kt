package com.baylonedward.player_roster.data.local.enums

/**
 * Created by: ebaylon.
 * Created on: 10/11/2021.
 */
enum class PlayerPosition(val title: String) {
    CENTER("Center"),
    POWER_FORWARD("Power Forward"),
    SMALL_FORWARD("Small Forward"),
    SHOOTING_GUARD("Shooting Guard"),
    POINT_GUARD("Point Guard");

    override fun toString(): String {
        return title
    }
}