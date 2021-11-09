package com.baylonedward.player_roster.utilities.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by: ebaylon.
 * Created on: 09/11/2021.
 */
class BasicListAdapter(private val listItemInterface: BasicListItemInterface): RecyclerView.Adapter<BasicListItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasicListItemViewHolder {
        return BasicListItemViewHolder.newInstance(parent)
    }

    override fun onBindViewHolder(holder: BasicListItemViewHolder, position: Int) {
        holder.onBind(
            title = listItemInterface.getTitle(position),
            onClick = listItemInterface.getOnClick(position)
        )
    }

    override fun getItemCount(): Int {
        return listItemInterface.getCount()
    }
}