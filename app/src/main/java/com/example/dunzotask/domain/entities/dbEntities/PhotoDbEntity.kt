package com.example.dunzotask.domain.entities.dbEntities

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class PhotoDbEntity(
    @Id var idPhoto: Long = 0,
    val id: String,
    val owner: String,
    val secret: String,
    val server: String,
    val farm: String,
    val title: String,
    val isPublic: Int,
    val isFriend: Int,
    val isFamily: Int
)