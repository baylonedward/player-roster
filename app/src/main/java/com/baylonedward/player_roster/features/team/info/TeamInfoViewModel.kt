package com.baylonedward.player_roster.features.team.info

import androidx.lifecycle.ViewModel
import com.baylonedward.player_roster.data.local.room.entity.player.Player
import com.baylonedward.player_roster.utilities.SingleLiveData
import com.baylonedward.player_roster.utilities.extensions.changeList
import com.baylonedward.player_roster.utilities.ui.BasicListItemInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

/**
 * Created by: ebaylon.
 * Created on: 11/11/2021.
 */
@ExperimentalCoroutinesApi
@HiltViewModel
class TeamInfoViewModel @Inject constructor() : ViewModel(), BasicListItemInterface {

    // fragment action
    private val _fragmentAction = SingleLiveData<TeamInfoFragment.Actions>(null)
    val fragmentAction = _fragmentAction.toLiveData()

    // players list
    private val list = ArrayList<Player>()

    // players list state
    private val _playersListState = SingleLiveData<List<Player>>(null)
    val playerListState = _playersListState.toLiveData()

    fun setPlayers(players: List<Player>) {
        _playersListState.value = players
        list.changeList(players)
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getTitle(position: Int): String {
        return getPlayer(position).listDisplayName()
    }

    override fun getOnClick(position: Int): () -> Unit = {
        val player = getPlayer(position)
        _fragmentAction.value = TeamInfoFragment.Actions.SelectPlayer(player)
    }

    private fun getPlayer(position: Int): Player {
        return list[position]
    }
}