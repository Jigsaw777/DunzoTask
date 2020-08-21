package com.example.dunzotask.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dunzotask.data.constants.AppConstants
import com.example.dunzotask.domain.entities.PhotoEntity
import com.example.dunzotask.domain.entities.PhotoListEntity
import com.example.dunzotask.domain.requests.GetSearchItemsRequest
import com.example.dunzotask.domain.usecases.GetImageSearchResultsUseCase
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel @ViewModelInject constructor(private val getImageSearchResultsUseCase: GetImageSearchResultsUseCase) :
    ViewModel() {

    private val resultsLD = MutableLiveData<PhotoListEntity>()
    private var pageNumber = 1
    private val compositeDisposable = CompositeDisposable()
    private val errorLD = MutableLiveData<String>()
    private var totalPages: Long = 999999L

    var isScrolling: Boolean = false
    var searchTerm: String = ""

    var rvLastPosition = 4

    val searchItemsResult: LiveData<PhotoListEntity>
        get() = resultsLD

    val errorLiveData: LiveData<String>
        get() = errorLD

    var savedList = mutableListOf<PhotoEntity>()
    var isNetworkFetched = false

    fun getSearchResults(shouldIncrementPageNumber: Boolean = false) {
        if (pageNumber <= totalPages) {
            if (shouldIncrementPageNumber)
                pageNumber += 1
            isNetworkFetched = true
            val request = GetSearchItemsRequest(pageNumber, searchTerm)
            getImageSearchResultsUseCase.getSearcheResuts(request)
                .subscribeOn(
                    Schedulers.io()
                ).subscribe({
                    resultsLD.postValue(it.photoListEntity)
                    totalPages =
                        if (it.photoListEntity.photos.isNotEmpty()) it.photoListEntity.pages else 99999L
                }, {
                    it.printStackTrace()
                    errorLD.postValue(it.localizedMessage)
                }).let {
                    compositeDisposable.add(it)
                }
        } else
            errorLD.postValue(AppConstants.VM_ERROR_MSG)
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        super.onCleared()
    }
}