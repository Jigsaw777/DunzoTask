package com.example.dunzotask.ui.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.dunzotask.domain.entities.dbEntities.SearchHistoryEntity
import kotlinx.android.synthetic.main.history_item_layout.view.*

class SearchHistoryItemVH(
    private val view: View,
    private val onSearchItemClicked: (searchEntity: SearchHistoryEntity) -> Unit
) : RecyclerView.ViewHolder(view) {
    fun bindData(searchHistoryEntity: SearchHistoryEntity) {
        view.tv_search_text.text = searchHistoryEntity.searchTerm
        view.tv_browsed_items.text = "${searchHistoryEntity.browsedItems} items"
        view.tv_time.text = searchHistoryEntity.time
        view.setOnClickListener {
            onSearchItemClicked.invoke(searchHistoryEntity)
        }
    }
}