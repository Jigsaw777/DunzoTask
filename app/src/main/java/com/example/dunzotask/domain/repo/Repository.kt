package com.example.dunzotask.domain.repo

import com.example.dunzotask.domain.entities.PhotoResponseEntity
import com.example.dunzotask.domain.requests.GetSearchItemsRequest
import io.reactivex.rxjava3.core.Single

interface Repository{
    fun getSearchResults(getSearchItemsRequest: GetSearchItemsRequest): Single<PhotoResponseEntity>
}