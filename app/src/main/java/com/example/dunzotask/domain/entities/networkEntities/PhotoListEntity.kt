package com.example.dunzotask.domain.entities.networkEntities

import com.google.gson.annotations.SerializedName

data class PhotoListEntity(
    @SerializedName("page") val page: Int,
    @SerializedName("pages") val pages: Long,
    @SerializedName("perpage") val perPage: Int,
    @SerializedName("total") val total: String,
    @SerializedName("photo") val photos: List<PhotoEntity>
)