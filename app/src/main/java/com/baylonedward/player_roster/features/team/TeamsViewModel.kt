package com.baylonedward.player_roster.features.team

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.baylonedward.player_roster.data.local.room.entity.team.Team
import com.baylonedward.player_roster.data.local.room.entity.team.TeamAndPlayers
import com.baylonedward.player_roster.data.repository.TeamsRepository
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
class TeamsViewModel @Inject constructor(
    private val teamsRepository: TeamsRepository
) : ViewModel(), BasicListItemInterface {
    // fragment action
    private val _fragmentAction = SingleLiveData<TeamsFragment.Actions>(null)
    val fragmentAction = _fragmentAction.toLiveData()

    // teams list
    private val teamsList = ArrayList<TeamAndPlayers>()

    // teams list state
    private val _teamsListState = MutableLiveData<State<List<TeamAndPlayers>>>(null)
    val teamsListState: LiveData<State<List<TeamAndPlayers>>> = _teamsListState

    /**
     * [BasicListItemInterface] methods for list of teams
     */
    override fun getCount(): Int {
        return teamsList.size
    }

    override fun getTitle(position: Int): String {
        return getTeamAndPlayers(position).team.name
    }

    override fun getOnClick(position: Int): () -> Unit = {
        val data = getTeamAndPlayers(position)
        _fragmentAction.value = TeamsFragment.Actions.SelectTeam(data)
    }

    private fun getTeamAndPlayers(position: Int): TeamAndPlayers {
        return teamsList[position]
    }

    private suspend fun createDummyTeams() {
        val cities = listOf("Davao", "Cebu", "Caloocan", "Makati", "Mandaluyong", "Laguna")
        val list = ArrayList<Team>().apply {
            for (i in 1..10) {
                val team = Team.newTeam(
                    name = "Team $i",
                    city = cities.random()
                )
                this.add(team)
            }
        }
        teamsRepository.addTeams(list)
    }

    suspend fun getAllTeams() {
        teamsRepository.allTeamsAndPlayers()
            .onEach { teamsList.changeList(it) }
            .catch { _teamsListState.value = State.Error(it.localizedMessage.orEmpty()) }
            .collect { _teamsListState.value = State.Success(it) }
    }
}