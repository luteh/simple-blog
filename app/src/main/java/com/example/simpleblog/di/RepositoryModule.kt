package com.example.simpleblog.di

import com.example.simpleblog.data.repository.MyRepositoryImpl
import com.example.simpleblog.domain.repository.MyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindMyRepository(myRepositoryImpl: MyRepositoryImpl): MyRepository
}