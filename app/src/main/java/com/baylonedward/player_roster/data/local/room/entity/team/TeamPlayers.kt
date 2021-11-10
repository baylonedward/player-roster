package com.baylonedward.player_roster.data.local.room.entity.team

import androidx.room.Embedded
import androidx.room.Relation
import com.baylonedward.player_roster.data.local.room.entity.player.Player

/**
 * Created by: ebaylon.
 * Created on: 10/11/2021.
 */
data class TeamPlayers(
    @Embedded val team: Team,
    @Relation(
        parentColumn = "id",
        entityColumn = "teamId"
    )
    val players: List<Player>
)
