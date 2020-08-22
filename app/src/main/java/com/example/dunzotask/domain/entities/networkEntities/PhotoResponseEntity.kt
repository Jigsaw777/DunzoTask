package com.example.dunzotask.domain.entities.networkEntities

import com.google.gson.annotations.SerializedName

data class PhotoResponseEntity(
    @SerializedName("photos") val photoListEntity: PhotoListEntity,
    @SerializedName("stats") val stat: String
)