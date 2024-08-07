package com.afoxplus.home.delivery.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afoxplus.home.domain.entities.RestaurantOrderType
import com.afoxplus.home.domain.usecases.SetRestaurantToCreateOrder
import com.afoxplus.restaurants.entities.Restaurant
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
    private val setRestaurantToCreateOrder: SetRestaurantToCreateOrder,
    private val coroutines: UIKitCoroutineDispatcher
) : ViewModel() {

    private val mNavigation: MutableSharedFlow<Navigation> by lazy { MutableSharedFlow() }
    val navigation: SharedFlow<Navigation> get() = mNavigation

    val onEventBusListener = eventBusListener.listen()

    fun onScanResponse(data: String) = viewModelScope.launch(coroutines.getIODispatcher()) {
        setRestaurantToCreateOrder(data)
        mNavigation.emit(Navigation.GoToMarketOrder)
    }

    fun setRestaurantFromDelivery(restaurant: Restaurant) =
        viewModelScope.launch(coroutines.getMainDispatcher()) {
            setRestaurantToCreateOrder(restaurant, RestaurantOrderType.DELIVERY)
            mNavigation.emit(Navigation.GoToMarketOrder)
        }

    fun setRestaurantFromTable(restaurant: Restaurant) =
        viewModelScope.launch(coroutines.getMainDispatcher()) {
            setRestaurantToCreateOrder(restaurant, RestaurantOrderType.TABLE)
            mNavigation.emit(Navigation.GoToMarketOrder)
        }

    sealed class Navigation {
        object GoToMarketOrder : Navigation()
    }
}