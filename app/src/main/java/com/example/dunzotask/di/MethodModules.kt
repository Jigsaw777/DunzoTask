package com.example.dunzotask.di

import com.example.dunzotask.data.constants.AppConstants
import com.example.dunzotask.data.implementation.RepoImpl
import com.example.dunzotask.data.services.localServices.GetFromLocalDBServices
import com.example.dunzotask.data.services.localServices.PostToLocalDBServices
import com.example.dunzotask.data.services.networkServices.GetServices
import com.example.dunzotask.domain.repo.Repository
import com.example.dunzotask.domain.usecases.AddSearchHistoryItemUseCase
import com.example.dunzotask.domain.usecases.GetImageSearchResultsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object MethodModules {

    @Provides
    fun provideSearchResultsUseCase(repository: Repository): GetImageSearchResultsUseCase {
        return GetImageSearchResultsUseCase(repository)
    }

    @Provides
    fun addSearchHistoryItemUseCase(repository: Repository): AddSearchHistoryItemUseCase{
        return AddSearchHistoryItemUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create()).baseUrl(AppConstants.BASE_URL)
            .client(httpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder().connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS).build()
    }

    @Provides
    @Singleton
    fun getFetchResultService(retrofit: Retrofit): GetServices {
        return retrofit.create(GetServices::class.java)
    }

    @Provides
    @Singleton
    fun getFromLocalDBServices():GetFromLocalDBServices{
        return GetFromLocalDBServices()
    }

    @Provides
    @Singleton
    fun postToLocalDbServices():PostToLocalDBServices{
        return PostToLocalDBServices()
    }

    @Provides
    @Singleton
    fun getRepoImpl(
        service: GetServices,
        getFromLocalService: GetFromLocalDBServices,
        postToLocalService: PostToLocalDBServices
    ): RepoImpl {
        return RepoImpl(service, getFromLocalService, postToLocalService)
    }
}