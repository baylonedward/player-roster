package com.baylonedward.player_roster.features.team.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.baylonedward.player_roster.databinding.FragmentAddTeamDialogBinding
import com.baylonedward.player_roster.utilities.State
import com.baylonedward.player_roster.utilities.base.BaseDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

/**
 * Created by: ebaylon.
 * Created on: 10/11/2021.
 */
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class AddTeamDialogFragment(supportFragmentManager: FragmentManager) :
    BaseDialogFragment<FragmentAddTeamDialogBinding>(supportFragmentManager, TAG) {

    private val viewModel by viewModels<AddTeamViewModel>()

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddTeamDialogBinding {
        return FragmentAddTeamDialogBinding.inflate(layoutInflater)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        isCancelable = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.newTeamInstance()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // UI
        setUI()

        // states
        observeStates()
    }

    override fun dismiss() {
        if (this.dialog?.isShowing == true) super.dismiss()
    }

    private fun setUI() {
        // name
        binding.textInputName.doOnTextChanged { text, _, _, _ ->
            viewModel.updateName(text.toString())
        }
        // city
        binding.textInputCity.doOnTextChanged { text, _, _, _ ->
            viewModel.updateCity(text.toString())
        }
        // size
        binding.textInputSize.doOnTextChanged { text, _, _, _ ->
            viewModel.updateSize(text.toString())
        }
        // add button
        binding.buttonAdd.setOnClickListener { lifecycleScope.launch { viewModel.addNewTeam() } }
        // cancel button
        binding.buttonCancel.setOnClickListener { dismiss() }
    }

    private fun observeStates() {
        // new team state
        viewModel.newTeamState.observe(viewLifecycleOwner) { state ->
            if (state == null) return@observe

            when (state) {
                is State.Success -> {
                    showToastMessage(state.message)
                    clearInput()
                    dismiss()
                }
                else -> showToastMessage(state.message)
            }
        }
    }

    private fun clearInput() {
        listOf(
            binding.textInputName,
            binding.textInputCity,
            binding.textInputSize
        ).onEach { it.text = null }
    }

    companion object {
        val TAG = AddTeamDialogFragment::class.java.name
    }
}