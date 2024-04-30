package com.afoxplus.home.delivery.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afoxplus.home.usecases.actions.SetContextFromScanQR
import com.afoxplus.home.usecases.actions.SetContextWithDelivery
import com.afoxplus.restaurants.delivery.models.RestaurantEventModel
import com.afoxplus.uikit.bus.UIKitEventBusWrapper
import com.afoxplus.uikit.di.UIKitCoroutineDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val eventBusListener: UIKitEventBusWrapper,
    private val setContextFromScanQR: SetContextFromScanQR,
    private val setContextWithDelivery: SetContextWithDelivery,
    private val coroutines: UIKitCoroutineDispatcher
) : ViewModel() {

    private val mNavigation: MutableSharedFlow<Navigation> by lazy { MutableSharedFlow() }
    val navigation: SharedFlow<Navigation> get() = mNavigation

    val onEventBusListener = eventBusListener.listen()

    fun onScanResponse(data: String) = viewModelScope.launch(coroutines.getMainDispatcher()) {
        setContextFromScanQR(data)
        mNavigation.emit(Navigation.GoToMarketOrder)
    }

    fun setContextDeliveryAndGoToMarket(restaurant: RestaurantEventModel) =
        viewModelScope.launch(coroutines.getMainDispatcher()) {
            setContextWithDelivery(restaurant)
            mNavigation.emit(Navigation.GoToMarketOrder)
        }

    sealed class Navigation {
        object GoToMarketOrder : Navigation()
    }
}