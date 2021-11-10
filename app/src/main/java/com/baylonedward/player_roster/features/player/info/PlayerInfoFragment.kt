package com.baylonedward.player_roster.features.player.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.baylonedward.player_roster.data.local.room.entity.player.Player
import com.baylonedward.player_roster.databinding.FragmentPlayerInfoBinding
import com.baylonedward.player_roster.utilities.base.BaseFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * Created by: ebaylon.
 * Created on: 11/11/2021.
 */
@ExperimentalCoroutinesApi
class PlayerInfoFragment : BaseFragment<FragmentPlayerInfoBinding>() {
    private val args by navArgs<PlayerInfoFragmentArgs>()

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPlayerInfoBinding {
        return FragmentPlayerInfoBinding.inflate(layoutInflater)
    }

    override fun onStart() {
        super.onStart()
        navigationViewModel.showBottomNavigation(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // toolbar
        setToolbar()

        // set data
        setData(args.player)
    }

    override fun onStop() {
        super.onStop()
        navigationViewModel.showBottomNavigation()
    }

    private fun setToolbar() {
        binding.toolbar.setNavigationOnClickListener { navigationViewModel.popBackScreen() }
    }

    private fun setData(player: Player) {
        // name
        binding.textViewName.text = player.name

        // team
        binding.textInputTeam.setText(player.team?.name)

        // height
        binding.textInputHeight.setText(player.height.toString())

        // weight
        binding.textInputWeight.setText(player.weight.toString())

        // jump height
        binding.textInputJumpHeight.setText(player.jumpHeight.toString())

        // gender
        binding.textInputGender.setText(player.gender)

        // position
        binding.textInputPosition.setText(player.position)
    }
}