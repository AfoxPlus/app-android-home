package com.afoxplus.home.delivery.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afoxplus.home.usecases.actions.SetDataToContext
import com.afoxplus.products.delivery.views.events.OnClickProductOfferEvent
import com.afoxplus.restaurants.delivery.flow.RestaurantBridge
import com.afoxplus.restaurants.entities.Restaurant
import com.afoxplus.uikit.bus.UIKitEvent
import com.afoxplus.uikit.bus.UIKitEventBusWrapper
import com.afoxplus.uikit.di.UIKitCoroutineDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val productEventBus: UIKitEventBusWrapper,
    private val restaurantBridge: RestaurantBridge,
    private val setDataToContext: SetDataToContext,
    private val coroutines: UIKitCoroutineDispatcher
) : ViewModel() {

    private val mHomeRestaurantClicked: MutableLiveData<Restaurant> by lazy { MutableLiveData<Restaurant>() }
    private val mProductOfferClicked: MutableLiveData<UIKitEvent<Unit>> by lazy { MutableLiveData<UIKitEvent<Unit>>() }
    private val mNavigation: MutableSharedFlow<Navigation> by lazy { MutableSharedFlow() }

    val productOfferClicked: LiveData<UIKitEvent<Unit>> get() = mProductOfferClicked
    val homeRestaurantClicked: LiveData<Restaurant> get() = mHomeRestaurantClicked
    val navigation: SharedFlow<Navigation> get() = mNavigation

    init {
        viewModelScope.launch(coroutines.getMainDispatcher()) {
            restaurantBridge.fetchRestaurant().observeForever { restaurant ->
                mHomeRestaurantClicked.postValue(restaurant)
            }
        }

        viewModelScope.launch(coroutines.getMainDispatcher()) {
            productEventBus.getBusEventFlow().collectLatest { event ->
                if (event is OnClickProductOfferEvent) {
                    mProductOfferClicked.postValue(UIKitEvent(Unit))
                }
            }
        }
    }

    fun onScanResponse(data: String) = viewModelScope.launch(coroutines.getMainDispatcher()) {
        setDataToContext(data)
        mNavigation.emit(Navigation.GoToMarketOrder)
    }

    sealed class Navigation {
        object GoToMarketOrder : Navigation()
    }
}