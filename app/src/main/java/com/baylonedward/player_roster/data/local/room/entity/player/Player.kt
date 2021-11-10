package com.baylonedward.player_roster.data.local.room.entity.player

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.baylonedward.player_roster.data.local.enums.Gender
import com.baylonedward.player_roster.data.local.enums.PlayerPosition
import com.baylonedward.player_roster.data.local.room.entity.team.Team
import kotlinx.parcelize.Parcelize

/**
 * Created by: ebaylon.
 * Created on: 09/11/2021.
 */

@Parcelize
@Entity
data class Player(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val height: Double,
    val weight: Double,
    val gender: String,
    val picture: String,
    val jumpHeight: Double,
    val position: String,
    @Embedded(prefix = "team_") val team: Team?
) : Parcelable {

    fun listDisplayName(): String {
        return "$name - $position"
    }

    companion object {
        fun newPlayer(
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
                name = name,
                height = height,
                weight = weight,
                gender = gender.title,
                jumpHeight = jumpHeight,
                position = position.title,
                picture = picture,
                team = null
            )
        }
    }
}
