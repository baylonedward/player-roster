package com.baylonedward.player_roster.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.baylonedward.player_roster.data.local.room.entity.team.Team
import com.baylonedward.player_roster.data.local.room.entity.team.TeamAndPlayers
import com.baylonedward.player_roster.utilities.base.BaseDao
import kotlinx.coroutines.flow.Flow

/**
 * Created by: ebaylon.
 * Created on: 09/11/2021.
 */
@Dao
interface TeamDao: BaseDao<Team> {
    @Query("SELECT * FROM Team")
    fun all(): Flow<List<Team>>

    @Query("SELECT * FROM Team WHERE id = :teamId")
    fun get(teamId: Long): Flow<Team>

    @Transaction
    @Query("SELECT * FROM Team WHERE id = :teamId")
    fun teamAndPlayers(teamId: Long): TeamAndPlayers

    @Transaction
    @Query("SELECT * FROM Team")
    fun teamsAndPlayers(): Flow<List<TeamAndPlayers>>
}