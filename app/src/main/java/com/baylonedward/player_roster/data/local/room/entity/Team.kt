package com.baylonedward.player_roster.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by: ebaylon.
 * Created on: 09/11/2021.
 */
@Entity
data class Team(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val size: Int,
    val city: String
) {

    companion object {
        fun newTeam(name: String, size: Int = 15, city: String): Team {
            return Team(id = 0, name = name, size = size, city = city)
        }
    }
}
