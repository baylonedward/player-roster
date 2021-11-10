package com.baylonedward.player_roster.features.player

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.baylonedward.player_roster.data.local.room.entity.player.Player
import com.baylonedward.player_roster.databinding.FragmentPlayersBinding
import com.baylonedward.player_roster.features.player.add.AddPlayerDialogFragment
import com.baylonedward.player_roster.features.team.TeamsFragment
import com.baylonedward.player_roster.utilities.State
import com.baylonedward.player_roster.utilities.base.BaseFragment
import com.baylonedward.player_roster.utilities.ui.BasicListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class PlayersFragment : BaseFragment<FragmentPlayersBinding>() {
    private val viewModel by viewModels<PlayersViewModel>()
    private val playersListAdapter by lazy { BasicListAdapter(viewModel) }
    private val addPlayerDialog by lazy { AddPlayerDialogFragment(requireActivity().supportFragmentManager) }

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPlayersBinding {
        return FragmentPlayersBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // get all players
        lifecycleScope.launch { viewModel.getAllPlayers() }
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
        binding.listView.adapter = playersListAdapter
        // add button
        binding.buttonAdd.setOnClickListener {
            addPlayerDialog.show()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeStates() {
        // teams list
        viewModel.playersListState.observe(viewLifecycleOwner) { state ->
            if (state == null) return@observe

            when (state) {
                is State.Success -> state.value?.also { playersListAdapter.notifyDataSetChanged() }
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
                is Actions.SelectPlayer -> {
                    // navigate to player info screen
                }
            }
        }
    }


    sealed interface Actions {
        data class SelectPlayer(private val player: Player): Actions
    }
}