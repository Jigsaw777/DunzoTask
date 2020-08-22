package com.example.dunzotask.ui.viewmodels

import android.annotation.SuppressLint
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dunzotask.data.constants.AppConstants
import com.example.dunzotask.domain.entities.dbEntities.SearchHistoryEntity
import com.example.dunzotask.domain.entities.networkEntities.PhotoEntity
import com.example.dunzotask.domain.entities.networkEntities.PhotoListEntity
import com.example.dunzotask.domain.requests.GetSearchItemsRequest
import com.example.dunzotask.domain.usecases.AddSearchHistoryItemUseCase
import com.example.dunzotask.domain.usecases.GetImageSearchResultsUseCase
import com.example.dunzotask.utils.NetworkUtils
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel @ViewModelInject constructor(
    private val getImageSearchResultsUseCase: GetImageSearchResultsUseCase,
    private val addSearchHistoryItemUseCase: AddSearchHistoryItemUseCase
) :
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

    var cachedListForConfigurationChange = mutableListOf<PhotoEntity>()
    var isNetworkFetched = false
    var historyPicturesList = mutableListOf<PhotoEntity>()
    var timeOfSearch = ""
    var isFragmentVisible=false

    @SuppressLint("CheckResult")
    fun getSearchResults(shouldIncrementPageNumber: Boolean = false) {
        NetworkUtils.hasInternetConnection().subscribe { hasInternet ->
            if (hasInternet) {
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
                            if (it.photoListEntity.photos.isNotEmpty()) {
                                historyPicturesList.addAll(
                                    pageNumber - 1,
                                    it.photoListEntity.photos
                                )
                            }
                        }, {
                            it.printStackTrace()
                            errorLD.postValue(it.localizedMessage)
                        }).let {
                            compositeDisposable.add(it)
                        }
                } else
                    errorLD.postValue(AppConstants.VM_ERROR_MSG)
            } else {
                errorLD.postValue(AppConstants.NO_NETWORK_ERROR)
            }
        }
    }

    fun resetPageNumber() {
        pageNumber = 1
    }

    fun saveDataToLocal() {
        /** this would be executed as soon as activity gets killed. Making a variable and then saving it takes time
         * hence saved it directly. */
        addSearchHistoryItemUseCase.addSearchItem(
            SearchHistoryEntity(
                browsedItems = historyPicturesList.size.toLong(),
                searchTerm = searchTerm,
                time = timeOfSearch
            )
        )
        Log.d("vm","total : ${historyPicturesList.size}")
        historyPicturesList.clear()
    }

    override fun onCleared() {
        if (historyPicturesList.isNotEmpty() && searchTerm.isNotEmpty()) {
            addSearchHistoryItemUseCase.addSearchItem(
                SearchHistoryEntity(
                    browsedItems = historyPicturesList.size.toLong(),
                    searchTerm = searchTerm,
                    time = timeOfSearch
                )
            )
            Log.d("vm","total : ${historyPicturesList.size}")
            historyPicturesList.clear()
        }
        compositeDisposable.dispose()
        compositeDisposable.clear()
        super.onCleared()
    }
}