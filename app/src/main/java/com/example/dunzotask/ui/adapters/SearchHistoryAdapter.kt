package com.example.dunzotask.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dunzotask.R
import com.example.dunzotask.domain.entities.dbEntities.SearchHistoryEntity
import com.example.dunzotask.ui.viewholders.SearchHistoryItemVH

class SearchHistoryAdapter(private val onSearchItemClicked: (searchEntity: SearchHistoryEntity) -> Unit) :
    RecyclerView.Adapter<SearchHistoryItemVH>() {

    private var searchHistoryItems = mutableListOf<SearchHistoryEntity>()

    fun setAttributes(items: List<SearchHistoryEntity>) {
        searchHistoryItems.addAll(items.toMutableList())
        searchHistoryItems.reverse()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryItemVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.history_item_layout, parent, false)
        return SearchHistoryItemVH(view,onSearchItemClicked)
    }

    override fun onBindViewHolder(holder: SearchHistoryItemVH, position: Int) {
        holder.bindData(searchHistoryItems[position])
    }

    override fun getItemCount(): Int = searchHistoryItems.size

}