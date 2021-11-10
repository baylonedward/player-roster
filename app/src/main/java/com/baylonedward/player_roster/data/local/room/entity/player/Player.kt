package com.baylonedward.player_roster.data.local.room.entity.player

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.baylonedward.player_roster.data.local.enums.Gender
import com.baylonedward.player_roster.data.local.enums.PlayerPosition

/**
 * Created by: ebaylon.
 * Created on: 09/11/2021.
 */
@Entity
data class Player(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val teamId: Long,
    val name: String,
    val height: Double,
    val weight: Double,
    val gender: String,
    val picture: String,
    val jumpHeight: Double,
    val position: String
) {

    fun listDisplayName(): String {
        return "$name - $position"
    }

    companion object {
        fun newPlayer(
            teamId: Long,
            name: String,
            height: Double,
            weight: Double,
            gender: Gender,
            jumpHeight: Double,
            position: PlayerPosition,
            picture: String
        ): Player {
            return Player(
                id = 0,
                teamId = teamId,
                name = name,
                height = height,
                weight = weight,
                gender = gender.title,
                jumpHeight = jumpHeight,
                position = position.title,
                picture = picture
            )
        }
    }
}
