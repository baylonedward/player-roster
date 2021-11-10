package com.baylonedward.player_roster.features.player.add

import androidx.lifecycle.ViewModel
import com.baylonedward.player_roster.data.local.enums.Gender
import com.baylonedward.player_roster.data.local.enums.PlayerPosition
import com.baylonedward.player_roster.data.local.model.NewPlayer
import com.baylonedward.player_roster.data.local.room.entity.team.Team
import com.baylonedward.player_roster.data.repository.PlayersRepository
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
class AddPlayerViewModel @Inject constructor(
    private val teamsRepository: TeamsRepository,
    private val playersRepository: PlayersRepository
) : ViewModel() {

    // new player
    private val _newPlayer = NewPlayer()

    // add new team state
    private val _newPlayerState = SingleLiveData<State<Boolean>>(null)
    val newPlayerState = _newPlayerState.toLiveData()

    fun newPlayerInstance() {
        _newPlayer.clearValues()
    }

    suspend fun getAvailableTeams() = teamsRepository.availableTeams()

    fun getGenders() = Gender.values().toList()

    fun getPositions() = PlayerPosition.values().toList()

    fun updateName(value: String) {
        if (_newPlayer.name == value) return

        // new value, update.
        _newPlayer.name = value
    }

    fun updateTeam(value: Team) {
        if (_newPlayer.team == value) return

        // new value, update.
        _newPlayer.team = value
    }

    fun updateHeight(value: Double) {
        if (_newPlayer.height == value) return

        // new value, update.
        _newPlayer.height = value
    }

    fun updateWeight(value: Double) {
        if (_newPlayer.weight == value) return

        // new value, update.
        _newPlayer.weight = value
    }

    fun updateGender(value: Gender) {
        if (_newPlayer.gender == value) return

        // new value, update.
        _newPlayer.gender = value
    }

    fun updateJumpHeight(value: Double) {
        if (_newPlayer.jumpHeight == value) return

        // new value, update.
        _newPlayer.jumpHeight = value
    }

    fun updatePosition(value: PlayerPosition) {
        if (_newPlayer.position == value) return

        // new value, update.
        _newPlayer.position = value
    }

    suspend fun addNewPlayer() {
        val newPlayer = _newPlayer.toPlayer() ?: return

        playersRepository.addPlayer(newPlayer).also {
            _newPlayerState.value = if (it) State.Success(
                it,
                message = "New player added!"
            ) else State.Failed("Unable to add player")
        }
    }
}