package com.example.dunzotask.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dunzotask.domain.requests.GetSearchItemsRequest
import com.example.dunzotask.domain.usecases.GetImageSearchResultsUseCase
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel @ViewModelInject constructor(private val getImageSearchResultsUseCase: GetImageSearchResultsUseCase) :
    ViewModel() {

    val number = MutableLiveData<Long>()

    fun getNumber() {
        getImageSearchResultsUseCase.getSearcheResuts(GetSearchItemsRequest(searchTerm = "hello"))
            .subscribeOn(
                Schedulers.io()
            ).subscribe({
                number.postValue(it.photoListEntity.pages)
            }, {
                it.printStackTrace()
            })
    }
}