package com.afoxplus.home.delivery.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afoxplus.home.usecases.actions.SetContextFromScanQR
import com.afoxplus.home.usecases.actions.SetContextWithDelivery
import com.afoxplus.products.delivery.views.events.OnClickProductOfferEvent
import com.afoxplus.restaurants.delivery.views.events.OnClickDeliveryEvent
import com.afoxplus.restaurants.delivery.views.events.OnClickRestaurantHomeEvent
import com.afoxplus.restaurants.entities.Restaurant
import com.afoxplus.uikit.bus.UIKitEventBusWrapper
import com.afoxplus.uikit.di.UIKitCoroutineDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val eventBus: UIKitEventBusWrapper,
    private val setContextFromScanQR: SetContextFromScanQR,
    private val setContextWithDelivery: SetContextWithDelivery,
    private val coroutines: UIKitCoroutineDispatcher
) : ViewModel() {

    private val mNavigation: MutableSharedFlow<Navigation> by lazy { MutableSharedFlow() }
    val navigation: SharedFlow<Navigation> get() = mNavigation

    val onOpenScanEvent = eventBus.getBusEventFlow().flowOn(coroutines.getMainDispatcher())
        .filter { event -> event is OnClickRestaurantHomeEvent || event is OnClickProductOfferEvent }
        .shareIn(scope = viewModelScope, started = SharingStarted.Eagerly)

    init {
        viewModelScope.launch(coroutines.getMainDispatcher()) {
            eventBus.getBusEventFlow()
                .filter { event -> event is OnClickDeliveryEvent }
                .map { event -> event as OnClickDeliveryEvent }
                .collectLatest { event -> setContextDeliveryAndGoToMarket(event.restaurant) }
        }
    }

    fun onScanResponse(data: String) = viewModelScope.launch(coroutines.getMainDispatcher()) {
        setContextFromScanQR(data)
        mNavigation.emit(Navigation.GoToMarketOrder)
    }

    private fun setContextDeliveryAndGoToMarket(restaurant: Restaurant) =
        viewModelScope.launch(coroutines.getMainDispatcher()) {
            setContextWithDelivery(restaurant)
            mNavigation.emit(Navigation.GoToMarketOrder)
        }

    sealed class Navigation {
        object GoToMarketOrder : Navigation()
    }
}