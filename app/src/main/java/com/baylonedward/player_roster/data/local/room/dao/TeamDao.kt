package com.baylonedward.player_roster.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.baylonedward.player_roster.data.local.room.entity.Team
import kotlinx.coroutines.flow.Flow

/**
 * Created by: ebaylon.
 * Created on: 09/11/2021.
 */
@Dao
interface TeamDao: BaseDao<Team> {
    @Query("SELECT * FROM Team")
    fun all(): Flow<List<Team>>
}