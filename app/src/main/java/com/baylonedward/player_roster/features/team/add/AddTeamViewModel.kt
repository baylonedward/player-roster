package com.baylonedward.player_roster.features.team.add

import androidx.lifecycle.ViewModel
import com.baylonedward.player_roster.data.local.model.NewTeam
import com.baylonedward.player_roster.data.repository.TeamsRepository
import com.baylonedward.player_roster.utilities.SingleLiveData
import com.baylonedward.player_roster.utilities.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

/**
 * Created by: ebaylon.
 * Created on: 10/11/2021.
 */
@ExperimentalCoroutinesApi
@HiltViewModel
class AddTeamViewModel @Inject constructor(
    private val teamsRepository: TeamsRepository
) : ViewModel() {

    // new team
    private val _newTeam = NewTeam.emptyInstance()

    // add new team state
    private val _newTeamState = SingleLiveData<State<Boolean>>(null)
    val newTeamState = _newTeamState.toLiveData()

    fun newTeamInstance() {
        _newTeam.clearValues()
    }

    fun updateName(value: String) {
        if (_newTeam.name == value) return

        // new value, update.
        _newTeam.name = value
    }

    fun updateCity(value: String) {
        if (_newTeam.city == value) return

        // new value, update.
        _newTeam.city = value
    }

    fun updateSize(value: String) {
        if (value.toIntOrNull() == null || _newTeam.size == value.toInt()) return

        // new value, update.
        _newTeam.size = value.toInt()
    }

    suspend fun addNewTeam() {
        if (!_newTeam.isComplete()) return
        val newTeam = _newTeam.toTeam()

        teamsRepository.addTeam(newTeam).also {
            _newTeamState.value = if (it) State.Success(
                it,
                message = "New team added!"
            ) else State.Failed("Unable to add team")
        }
    }
}