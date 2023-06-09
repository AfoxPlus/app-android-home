package com.afoxplus.home.di

import com.afoxplus.home.usecases.SetContextFromScanQRUseCase
import com.afoxplus.home.usecases.SetContextWithDeliveryUseCase
import com.afoxplus.home.usecases.actions.SetContextFromScanQR
import com.afoxplus.home.usecases.actions.SetContextWithDelivery
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
internal interface HomeUseCaseModule {

    @Binds
    fun bindSetContextFromScanQRUseCase(impl: SetContextFromScanQRUseCase): SetContextFromScanQR

    @Binds
    fun bindSetContextWithDelivery(impl: SetContextWithDeliveryUseCase): SetContextWithDelivery
}