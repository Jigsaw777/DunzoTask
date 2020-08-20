package com.example.dunzotask.domain.usecases

import com.example.dunzotask.domain.entities.PhotoResponseEntity
import com.example.dunzotask.domain.repo.Repository
import com.example.dunzotask.domain.requests.GetSearchItemsRequest
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetImageSearchResultsUseCase @Inject constructor(val repository: Repository) {
    fun getSearcheResuts(getSearchItemsRequest: GetSearchItemsRequest): Single<PhotoResponseEntity> {
        return repository.getSearchResults(getSearchItemsRequest)
    }
}