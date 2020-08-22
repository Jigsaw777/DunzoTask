package com.example.dunzotask.domain.usecases

import com.example.dunzotask.domain.entities.dbEntities.SearchHistoryEntity
import com.example.dunzotask.domain.repo.Repository
import javax.inject.Inject

class AddSearchHistoryItemUseCase @Inject constructor(private val repository: Repository){
    fun addSearchItem(searchHistoryEntity: SearchHistoryEntity){
        return repository.addSingleHistoryItemToDB(searchHistoryEntity)
    }
}