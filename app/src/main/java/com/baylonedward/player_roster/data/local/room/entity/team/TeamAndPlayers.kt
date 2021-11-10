package com.baylonedward.player_roster.data.local.room.entity.team

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import com.baylonedward.player_roster.data.local.room.entity.player.Player
import kotlinx.parcelize.Parcelize

/**
 * Created by: ebaylon.
 * Created on: 10/11/2021.
 */
@Parcelize
data class TeamAndPlayers(
    @Embedded val team: Team,
    @Relation(
        parentColumn = "id",
        entityColumn = "team_id"
    )
    val players: List<Player>
): Parcelable
