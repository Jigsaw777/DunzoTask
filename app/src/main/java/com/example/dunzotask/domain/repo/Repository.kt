package com.example.dunzotask.domain.repo

import com.example.dunzotask.domain.entities.dbEntities.SearchHistoryEntity
import com.example.dunzotask.domain.entities.networkEntities.PhotoResponseEntity
import com.example.dunzotask.domain.requests.GetSearchItemsRequest
import io.reactivex.rxjava3.core.Single

interface Repository{
    fun getSearchResults(getSearchItemsRequest: GetSearchItemsRequest): Single<PhotoResponseEntity>
    fun addSingleHistoryItemToDB(searchHistoryEntity: SearchHistoryEntity)
    fun getSearchHistoryItems():List<SearchHistoryEntity>
}