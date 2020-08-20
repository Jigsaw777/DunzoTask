package com.example.dunzotask.data.services

import com.example.dunzotask.data.constants.AppConstants
import com.example.dunzotask.domain.entities.PhotoResponseEntity
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface GetServices {
    @GET(AppConstants.GET_IMAGES_ENDPOINT)
    fun getImages(@QueryMap data: Map<String, String>): Single<PhotoResponseEntity>
}