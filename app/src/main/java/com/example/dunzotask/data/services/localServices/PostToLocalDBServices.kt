package com.example.dunzotask.data.services.localServices

import com.example.dunzotask.domain.entities.dbEntities.SearchHistoryEntity
import com.example.dunzotask.utils.ObjectBox

class PostToLocalDBServices {
    private val searchHistoryBox = ObjectBox.boxStore.boxFor(SearchHistoryEntity::class.java)

    fun putSearchHistoryItem(searchHistoryEntity: SearchHistoryEntity) {
        searchHistoryBox.put(searchHistoryEntity)
    }
}