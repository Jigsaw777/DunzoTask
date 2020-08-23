package com.example.dunzotask.domain.usecases

import com.example.dunzotask.domain.entities.dbEntities.SearchHistoryEntity
import com.example.dunzotask.domain.repo.Repository
import javax.inject.Inject

class GetSearchListFromLocalUseCase @Inject constructor(private val repository: Repository) {
    fun getSearchList(): List<SearchHistoryEntity> {
        return repository.getSearchHistoryItems()
    }
}