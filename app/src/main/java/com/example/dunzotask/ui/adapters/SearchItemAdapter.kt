package com.example.dunzotask.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dunzotask.R
import com.example.dunzotask.domain.entities.PhotoEntity
import com.example.dunzotask.ui.viewholders.SearchItemVH

class SearchItemAdapter() : RecyclerView.Adapter<SearchItemVH>() {

    private var searchItems = mutableListOf<PhotoEntity>()

    fun setAttributes(items: List<PhotoEntity>) {
        val lastIndex = searchItems.size
        searchItems.addAll(lastIndex, items.toMutableList())
        notifyItemRangeInserted(lastIndex, items.size)
    }

    fun isEmpty(): Boolean {
        return searchItems.isEmpty()
    }

    fun getPhotosList():MutableList<PhotoEntity>{
        return searchItems
    }

    fun clear() {
        searchItems.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_item_view_layout, parent, false)
        return SearchItemVH(view)
    }

    override fun onBindViewHolder(holder: SearchItemVH, position: Int) {
        holder.bindData(searchItems[position])
    }

    override fun getItemCount(): Int = searchItems.size

}