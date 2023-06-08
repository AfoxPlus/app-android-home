package com.afoxplus.home.di

import com.afoxplus.home.usecases.SetDataToContextUseCase
import com.afoxplus.home.usecases.actions.SetDataToContext
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
internal interface HomeUseCaseModule {

    @Binds
    fun bindSetDataToContext(impl: SetDataToContextUseCase): SetDataToContext
}