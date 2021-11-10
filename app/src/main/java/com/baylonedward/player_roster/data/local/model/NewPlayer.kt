package com.baylonedward.player_roster.data.local.model

import com.baylonedward.player_roster.data.local.enums.Gender
import com.baylonedward.player_roster.data.local.enums.PlayerPosition
import com.baylonedward.player_roster.data.local.room.entity.player.Player
import com.baylonedward.player_roster.data.local.room.entity.team.Team

/**
 * Created by: ebaylon.
 * Created on: 10/11/2021.
 */
data class NewPlayer(
    var name: String? = null,
    var team: Team? = null,
    var height: Double? = null,
    var weight: Double? = null,
    var gender: Gender? = null,
    var jumpHeight: Double? = null,
    var position: PlayerPosition? = null,
    var picture: String = "", // will be updated later
) {

    private fun isComplete(): Boolean {
        if (name.isNullOrEmpty()) return false
        if (team == null) return false
        if (height == null) return false
        if (weight == null) return false
        if (gender == null) return false
        if (jumpHeight == null) return false
        if (position == null) return false

        return true
    }

    fun clearValues() {
        name = null
        team = null
        height = null
        weight = null
        gender = null
        jumpHeight = null
        position = null
    }

    fun toPlayer(): Player? {
        if (!this.isComplete()) return null

        // force unwrapping of properties is safe,
        // provided isComplete() method checks all necessary properties
        return Player(
            id = 0,
            name = name!!,
            height = height!!,
            weight = weight!!,
            gender = gender!!.title,
            jumpHeight = jumpHeight!!,
            position = position!!.title,
            picture = picture,
            team = team!!
        )
    }

}
