package com.example.dunzotask.domain.entities.dbEntities

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class SearchHistoryEntity(
    @Id var idSearchHistory: Long = 0,
    val browsedItems: Long,
    val searchTerm: String,
    val time: String
)