package com.example.dunzotask.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dunzotask.domain.entities.dbEntities.SearchHistoryEntity
import com.example.dunzotask.domain.usecases.GetSearchListFromLocalUseCase

class SearchFragmentViewModel @ViewModelInject constructor(private val getSearchListFromLocalUseCase: GetSearchListFromLocalUseCase) :
    ViewModel() {
    private val searchListLD = MutableLiveData<List<SearchHistoryEntity>>()
    var lastPosition = 0

    var cachedListForConfigChange= mutableListOf<SearchHistoryEntity>()
    var hasListBeenFetchedOnce=false

    val searchListLiveData: LiveData<List<SearchHistoryEntity>>
        get() = searchListLD

    /** Query builder can be used here to make fetching from local DB reactive.
     * For simplicity purposes haven't added reactive queries here. */
    fun getSearchList() {
        hasListBeenFetchedOnce=true
        searchListLD.postValue(getSearchListFromLocalUseCase.getSearchList())
    }
}