package com.example.dunzotask.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dunzotask.domain.entities.PhotoListEntity
import com.example.dunzotask.domain.requests.GetSearchItemsRequest
import com.example.dunzotask.domain.usecases.GetImageSearchResultsUseCase
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel @ViewModelInject constructor(private val getImageSearchResultsUseCase: GetImageSearchResultsUseCase) :
    ViewModel() {

    private val resultsLD = MutableLiveData<PhotoListEntity>()
    private var pageNumber = 1;
    private val compositeDisposable = CompositeDisposable()
    private val errorLD = MutableLiveData<String>()

    val searchItemsResult: LiveData<PhotoListEntity>
        get() = resultsLD

    val errorLiveData: LiveData<String>
        get() = errorLD

    fun getSearchResults(searchTerm: String) {
        val request = GetSearchItemsRequest(pageNumber, searchTerm)
        getImageSearchResultsUseCase.getSearcheResuts(request)
            .subscribeOn(
                Schedulers.io()
            ).subscribe({
                resultsLD.postValue(it.photoListEntity)
                pageNumber=it.photoListEntity.page
            }, {
                it.printStackTrace()
                errorLD.postValue(it.localizedMessage)
            }).let {
                compositeDisposable.add(it)
            }
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        super.onCleared()
    }
}