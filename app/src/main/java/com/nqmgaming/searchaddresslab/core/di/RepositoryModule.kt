package com.nqmgaming.searchaddresslab.core.di
import com.nqmgaming.searchaddresslab.data.repository.HereRepositoryImpl
import com.nqmgaming.searchaddresslab.domain.repository.HereRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindHereRepository(
        hereRepositoryImpl: HereRepositoryImpl
    ): HereRepository
}