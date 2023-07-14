package com.afoxplus.home.utils

import com.afoxplus.uikit.di.UIKitCoroutineDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class UIKitCoroutineDispatcherTest : UIKitCoroutineDispatcher {
    override fun getDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Unconfined

    override fun getIODispatcher(): CoroutineDispatcher = Dispatchers.Unconfined

    override fun getMainDispatcher(): CoroutineDispatcher = Dispatchers.Unconfined
}