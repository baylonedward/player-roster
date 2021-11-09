package com.baylonedward.player_roster.utilities.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.baylonedward.player_roster.R
import dagger.hilt.android.WithFragmentBindings

/**
 * Created by: ebaylon.
 * Created on: 21/12/2020.
 */
@WithFragmentBindings
abstract class BaseFragment<FragmentBinding : ViewBinding> : Fragment() {
    private var _binding: FragmentBinding? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = setBinding(inflater, container)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    abstract fun setBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentBinding

    fun showToastMessage(message: String?) {
        if (message == null) return
        if (message.isEmpty()) return
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    fun confirmDialog(
        title: String,
        message: String,
        positiveText: String = getString(R.string.ok),
        positiveAction: () -> Unit,
        negativeText: String = getString(R.string.cancel),
        negativeAction: (() -> Unit)? = null,
        cancellable: Boolean = true
    ) {
        AlertDialog.Builder(requireContext())
            .setCancelable(cancellable)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveText) { dialog, _ ->
                dialog.dismiss()
                positiveAction()
            }
            .setNegativeButton(negativeText) { dialog, _ ->
                dialog.dismiss()
                negativeAction?.invoke()
            }.show()
    }

    fun successDialog(
        title: String,
        message: String,
        positiveText: String = getString(R.string.ok),
        positiveAction: () -> Unit,
        cancellable: Boolean = false
    ) {
        AlertDialog.Builder(requireContext())
            .setCancelable(cancellable)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveText) { dialog, _ ->
                dialog.dismiss()
                positiveAction()
            }.show()
    }
}