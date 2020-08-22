package com.example.dunzotask.domain.entities.dbEntities

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class SearchHistoryEntity(
    @Id var idSearchHistory: Long = 0,
    val photos: List<PhotoDbEntity>,
    val searchTerm: String,
    val time: String
)