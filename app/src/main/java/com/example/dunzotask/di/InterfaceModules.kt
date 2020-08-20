package com.example.dunzotask.di

import com.example.dunzotask.data.implementation.RepoImpl
import com.example.dunzotask.domain.repo.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@InstallIn(ApplicationComponent::class)
@Module
abstract class InterfaceModules {

    @Binds
    abstract fun bindRepository(impl:RepoImpl):Repository
}