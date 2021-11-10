package com.baylonedward.player_roster.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.baylonedward.player_roster.data.local.room.entity.player.Player
import com.baylonedward.player_roster.utilities.base.BaseDao
import kotlinx.coroutines.flow.Flow

/**
 * Created by: ebaylon.
 * Created on: 09/11/2021.
 */
@Dao
interface PlayerDao: BaseDao<Player> {
    @Query("SELECT * FROM Player")
    fun all(): Flow<List<Player>>
}