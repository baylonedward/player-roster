package com.baylonedward.player_roster.features.team.info

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.baylonedward.player_roster.data.local.room.entity.player.Player
import com.baylonedward.player_roster.data.local.room.entity.team.Team
import com.baylonedward.player_roster.data.local.room.entity.team.TeamAndPlayers
import com.baylonedward.player_roster.databinding.FragmentTeamInfoBinding
import com.baylonedward.player_roster.utilities.base.BaseFragment
import com.baylonedward.player_roster.utilities.ui.BasicListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Created by: ebaylon.
 * Created on: 11/11/2021.
 */
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class TeamInfoFragment : BaseFragment<FragmentTeamInfoBinding>() {
    private val args by navArgs<TeamInfoFragmentArgs>()
    private val viewModel by viewModels<TeamInfoViewModel>()
    private val playersListAdapter by lazy { BasicListAdapter(viewModel) }

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTeamInfoBinding {
        return FragmentTeamInfoBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setPlayers(args.teamAndPlayers.players)
    }

    override fun onStart() {
        super.onStart()
        navigationViewModel.showBottomNavigation(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // set UI
        setUI()

        // set data
        setData(args.teamAndPlayers.team)

        // observe states
        observeStates()
    }

    private fun setUI() {
        // toolbar
        binding.toolbar.setNavigationOnClickListener { navigationViewModel.popBackScreen() }

        // players list adapter
        binding.listView.adapter = playersListAdapter
    }

    private fun setData(team: Team) {
        // name
        binding.textViewName.text = team.name

        // city
        binding.textInputCity.setText(team.city)

        // size
        binding.textInputSize.setText(team.size.toString())
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeStates() {
        // list state
        viewModel.playerListState.observe(viewLifecycleOwner) {
            if (it == null) return@observe
            playersListAdapter.notifyDataSetChanged()
        }

        // actions
        viewModel.fragmentAction.observe(viewLifecycleOwner) { action ->
            if (action == null) return@observe
            when (action) {
                is Actions.SelectPlayer -> navigationViewModel.playerInfoScreen(player = action.player)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        navigationViewModel.showBottomNavigation()
    }

    sealed interface Actions {
        data class SelectPlayer(val player: Player) : Actions
    }
}