package com.example.dunzotask.data.services.localServices

import com.example.dunzotask.domain.entities.dbEntities.SearchHistoryEntity
import com.example.dunzotask.utils.ObjectBox

class GetFromLocalDBServices {
    private val searchHistoryBox = ObjectBox.boxStore.boxFor(SearchHistoryEntity::class.java)

    fun getSearchHistoryItems(): List<SearchHistoryEntity> {
        return searchHistoryBox.all
    }
}