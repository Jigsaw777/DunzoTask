package com.example.dunzotask.data.implementation

import com.example.dunzotask.data.services.GetServices
import com.example.dunzotask.domain.entities.PhotoResponseEntity
import com.example.dunzotask.domain.repo.Repository
import com.example.dunzotask.domain.requests.GetSearchItemsRequest
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class RepoImpl @Inject constructor(val getServices: GetServices) : Repository{

    override fun getSearchResults(getSearchItemsRequest: GetSearchItemsRequest): Single<PhotoResponseEntity> {
        return getServices.getImages(getSearchItemsRequest.getParams())
    }

}