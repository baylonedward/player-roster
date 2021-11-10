package com.baylonedward.player_roster.data.local.model

import com.baylonedward.player_roster.data.local.room.entity.team.Team

/**
 * Created by: ebaylon.
 * Created on: 10/11/2021.
 */
data class NewTeam(
    var name: String,
    var city: String,
    var size: Int
) {

    fun isComplete(): Boolean {
        if (name.isEmpty()) return false
        if (city.isEmpty()) return false
        if (size < 1) return false

        return true
    }

    fun clearValues() {
        name = ""
        city = ""
        size = 0
    }

    fun toTeam(): Team {
        return Team(
            id = 0,
            name = this.name,
            city = this.city,
            size = this.size
        )
    }

    companion object {
        /**
         * Method to create new empty instance
         */
        fun emptyInstance(): NewTeam {
            return NewTeam(
                name = "",
                city = "",
                size = 0
            )
        }
    }
}
