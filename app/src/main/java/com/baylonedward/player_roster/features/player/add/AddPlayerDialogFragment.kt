package com.baylonedward.player_roster.features.player.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.baylonedward.player_roster.R
import com.baylonedward.player_roster.data.local.enums.Gender
import com.baylonedward.player_roster.data.local.enums.PlayerPosition
import com.baylonedward.player_roster.data.local.room.entity.team.Team
import com.baylonedward.player_roster.databinding.FragmentAddPlayerDialogBinding
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
class AddPlayerDialogFragment(
    supportFragmentManager: FragmentManager
) : BaseDialogFragment<FragmentAddPlayerDialogBinding>(supportFragmentManager, TAG) {

    private val viewModel by viewModels<AddPlayerViewModel>()

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddPlayerDialogBinding {
        return FragmentAddPlayerDialogBinding.inflate(layoutInflater)
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
        viewModel.newPlayerInstance()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // set UI
        setUI()

        // observe states
        observerStates()
    }

    private fun setUI() {
        // teams list adapter
        lifecycleScope.launch {
            val list = viewModel.getAvailableTeams() ?: return@launch
            binding.textInputTeam.apply {
                val adapter = ArrayAdapter(
                    requireContext(),
                    R.layout.layout_list_text_view_item,
                    list
                )
                setAdapter(adapter)
                setOnItemClickListener { adapterView, _, i, _ ->
                    val team = adapterView.adapter.getItem(i) as Team
                    lifecycleScope.launch { viewModel.updateTeam(team) }
                }
            }
        }

        // name
        binding.textInputName.doOnTextChanged { text, _, _, _ ->
            viewModel.updateName(text.toString())
        }

        // height
        binding.textInputHeight.doOnTextChanged { text, _, _, _ ->
            val value = text?.toString()

            value?.toDoubleOrNull()?.also {
                binding.textInputLayoutHeight.apply {
                    error = null
                    isErrorEnabled = false
                }
                viewModel.updateHeight(it)
                return@doOnTextChanged
            }

            if (value?.isNotEmpty() == true) {
                binding.textInputLayoutHeight.error = getString(R.string.invalid_value)
            }
        }

        // weight
        binding.textInputWeight.doOnTextChanged { text, _, _, _ ->
            val value = text?.toString()

            value?.toDoubleOrNull()?.also {
                binding.textInputLayoutWeight.apply {
                    error = null
                    isErrorEnabled = false
                }
                viewModel.updateWeight(it)
                return@doOnTextChanged
            }

            if (value?.isNotEmpty() == true) {
                binding.textInputLayoutWeight.error = getString(R.string.invalid_value)
            }
        }

        // jump height
        binding.textInputJumpHeight.doOnTextChanged { text, _, _, _ ->
            val value = text?.toString()

            value?.toDoubleOrNull()?.also {
                binding.textInputLayoutJumpHeight.apply {
                    error = null
                    isErrorEnabled = false
                }
                viewModel.updateJumpHeight(it)
                return@doOnTextChanged
            }

            if (value?.isNotEmpty() == true) {
                binding.textInputLayoutJumpHeight.error = getString(R.string.invalid_value)
            }
        }

        // gender list adapter
        lifecycleScope.launch {
            val list = viewModel.getGenders()
            binding.textInputGender.apply {
                val adapter = ArrayAdapter(
                    requireContext(),
                    R.layout.layout_list_text_view_item,
                    list
                )
                setAdapter(adapter)
                setOnItemClickListener { adapterView, _, i, _ ->
                    val gender = adapterView.adapter.getItem(i) as Gender
                    lifecycleScope.launch { viewModel.updateGender(gender) }
                }
            }
        }

        // position list adapter
        lifecycleScope.launch {
            val list = viewModel.getPositions()
            binding.textInputPosition.apply {
                val adapter = ArrayAdapter(
                    requireContext(),
                    R.layout.layout_list_text_view_item,
                    list
                )
                setAdapter(adapter)
                setOnItemClickListener { adapterView, _, i, _ ->
                    val position = adapterView.adapter.getItem(i) as PlayerPosition
                    lifecycleScope.launch { viewModel.updatePosition(position) }
                }
            }
        }

        // add button
        binding.buttonAdd.setOnClickListener {
            isFormValid()
            lifecycleScope.launch { viewModel.addNewPlayer() }
        }

        // cancel button
        binding.buttonCancel.setOnClickListener { dismiss() }
    }

    private fun observerStates() {
        // new player state
        viewModel.newPlayerState.observe(viewLifecycleOwner) { state ->
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

    private fun isFormValid() {
        listOf(
            binding.textInputLayoutTeam,
            binding.textInputLayoutName,
            binding.textInputLayoutHeight,
            binding.textInputLayoutWeight,
            binding.textInputLayoutJumpHeight,
            binding.textInputLayoutGender,
            binding.textInputLayoutPosition
        ).onEach {
            it.error = if (it.editText?.text.isNullOrEmpty()) {
                getString(R.string.required)
            } else {
                it.isErrorEnabled = false
                null
            }
        }
    }

    private fun clearInput() {
        listOf(
            binding.textInputTeam,
            binding.textInputName,
            binding.textInputHeight,
            binding.textInputWeight,
            binding.textInputJumpHeight,
            binding.textInputGender,
            binding.textInputPosition
        ).onEach { it.text = null }
    }

    companion object {
        private val TAG = AddPlayerDialogFragment::class.java.name
    }
}