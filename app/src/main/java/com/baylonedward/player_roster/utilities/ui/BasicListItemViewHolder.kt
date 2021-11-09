package com.baylonedward.player_roster.utilities.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.baylonedward.player_roster.databinding.LayoutListItemBinding

/**
 * Created by: ebaylon.
 * Created on: 09/11/2021.
 */
class BasicListItemViewHolder(private val binding: LayoutListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(title: String, onClick: () -> Unit) {
        binding.textView.text = title
        binding.root.setOnClickListener { onClick() }
    }

    companion object {
        fun newInstance(viewGroup: ViewGroup): BasicListItemViewHolder {
            val layoutInflater = LayoutInflater.from(viewGroup.context)
            val view = LayoutListItemBinding.inflate(layoutInflater, viewGroup, false)
            return BasicListItemViewHolder(view)
        }
    }
}