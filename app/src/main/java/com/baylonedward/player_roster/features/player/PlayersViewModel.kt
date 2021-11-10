package com.baylonedward.player_roster.features.player

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.baylonedward.player_roster.data.local.room.entity.player.Player
import com.baylonedward.player_roster.data.local.room.entity.team.Team
import com.baylonedward.player_roster.data.repository.PlayersRepository
import com.baylonedward.player_roster.features.team.TeamsFragment
import com.baylonedward.player_roster.utilities.SingleLiveData
import com.baylonedward.player_roster.utilities.State
import com.baylonedward.player_roster.utilities.extensions.changeList
import com.baylonedward.player_roster.utilities.ui.BasicListItemInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class PlayersViewModel @Inject constructor(
    private val playersRepository: PlayersRepository
) : ViewModel(), BasicListItemInterface {

    // fragment actions
    private val _fragmentAction = SingleLiveData<PlayersFragment.Actions>(null)
    val fragmentAction = _fragmentAction.toLiveData()

    // players list
    private val playersList = ArrayList<Player>()

    // players list state
    private val _playersListState = MutableLiveData<State<List<Player>>>(null)
    val playersListState: LiveData<State<List<Player>>> = _playersListState

    override fun getCount(): Int {
        return playersList.size
    }

    override fun getTitle(position: Int): String {
        return getPlayer(position).listDisplayName()
    }

    override fun getOnClick(position: Int): () -> Unit = {
        _fragmentAction.value = PlayersFragment.Actions.SelectPlayer(getPlayer(position))
    }

    private fun getPlayer(position: Int): Player {
        return playersList[position]
    }

    suspend fun getAllPlayers() {
        playersRepository.allPlayers()
            .onEach { playersList.changeList(it) }
            .catch { _playersListState.value = State.Error(it.localizedMessage.orEmpty()) }
            .collect { _playersListState.value = State.Success(it) }
    }
}