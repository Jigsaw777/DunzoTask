package com.example.dunzotask.data.implementation

import com.example.dunzotask.data.services.localServices.GetFromLocalDBServices
import com.example.dunzotask.data.services.localServices.PostToLocalDBServices
import com.example.dunzotask.data.services.networkServices.GetServices
import com.example.dunzotask.domain.entities.dbEntities.SearchHistoryEntity
import com.example.dunzotask.domain.entities.networkEntities.PhotoResponseEntity
import com.example.dunzotask.domain.repo.Repository
import com.example.dunzotask.domain.requests.GetSearchItemsRequest
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class RepoImpl @Inject constructor(
    private val getServices: GetServices,
    private val getFromLocalDBServices: GetFromLocalDBServices,
    private val postToLocalDBServices: PostToLocalDBServices
) : Repository {

    override fun getSearchResults(getSearchItemsRequest: GetSearchItemsRequest): Single<PhotoResponseEntity> {
        return getServices.getImages(getSearchItemsRequest.getParams())
    }

    override fun addSingleHistoryItemToDB(searchHistoryEntity: SearchHistoryEntity) {
        postToLocalDBServices.putSearchHistoryItem(searchHistoryEntity)
    }

    override fun getSearchHistoryItems(): List<SearchHistoryEntity> {
        return getFromLocalDBServices.getSearchHistoryItems()
    }
}