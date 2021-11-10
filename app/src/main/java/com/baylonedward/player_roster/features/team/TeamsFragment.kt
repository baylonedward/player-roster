package com.baylonedward.player_roster.features.team

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.baylonedward.player_roster.data.local.room.entity.team.Team
import com.baylonedward.player_roster.data.local.room.entity.team.TeamAndPlayers
import com.baylonedward.player_roster.databinding.FragmentTeamsBinding
import com.baylonedward.player_roster.features.team.add.AddTeamDialogFragment
import com.baylonedward.player_roster.utilities.State
import com.baylonedward.player_roster.utilities.base.BaseFragment
import com.baylonedward.player_roster.utilities.ui.BasicListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class TeamsFragment : BaseFragment<FragmentTeamsBinding>() {
    private val viewModel by viewModels<TeamsViewModel>()
    private val teamsListAdapter by lazy { BasicListAdapter(viewModel) }

    override fun setBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentTeamsBinding {
        return FragmentTeamsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // get all teams
        lifecycleScope.launch { viewModel.getAllTeams() }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // set UI
        setUI()

        // observe states
        observeStates()
    }

    private fun setUI() {
        // teams list adapter
        binding.listView.adapter = teamsListAdapter
        // add button
        binding.buttonAdd.setOnClickListener {
            navigationViewModel.addTeamScreen(requireActivity().supportFragmentManager)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeStates() {
        // teams list
        viewModel.teamsListState.observe(viewLifecycleOwner) { state ->
            if (state == null) return@observe

            when (state) {
                is State.Success -> state.value?.also { teamsListAdapter.notifyDataSetChanged() }
                is State.Loading -> {
                    // loading animation/dialog
                }
                else -> showToastMessage(state.message)
            }
        }

        // fragment action
        viewModel.fragmentAction.observe(viewLifecycleOwner) { action ->
            if (action == null) return@observe

            when (action) {
                is Actions.SelectTeam -> {
                    navigationViewModel.teamInfoScreen(teamAndPlayers = action.teamAndPlayers)
                }
            }
        }
    }

    sealed interface Actions {
        data class SelectTeam(val teamAndPlayers: TeamAndPlayers): Actions
    }
}