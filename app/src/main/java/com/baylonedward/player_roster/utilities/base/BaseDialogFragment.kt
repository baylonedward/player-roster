package com.baylonedward.player_roster.utilities.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding

/**
 * Created by: ebaylon.
 * Created on: 21/12/2020.
 */
abstract class BaseDialogFragment<FragmentBinding : ViewBinding>(
    private val supportFragmentManager: FragmentManager,
    private val fragmentTag: String
) : CustomDialogFragment() {
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

    override fun dismiss() {
        if (this.dialog?.isShowing == true) super.dismiss()
    }

    override fun show() {
        show(supportFragmentManager, fragmentTag)
    }

    abstract fun setBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentBinding

    protected fun showToastMessage(message: String?) {
        if (message == null) return
        if (message.isEmpty()) return
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

}