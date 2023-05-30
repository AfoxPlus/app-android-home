package com.afoxplus.home.delivery.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afoxplus.products.delivery.views.events.OnClickProductOfferEvent
import com.afoxplus.restaurants.delivery.flow.RestaurantBridge
import com.afoxplus.restaurants.entities.Restaurant
import com.afoxplus.uikit.bus.UIKitEvent
import com.afoxplus.uikit.bus.UIKitEventBusWrapper
import com.afoxplus.uikit.di.UIKitCoroutineDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productEventBus: UIKitEventBusWrapper,
    private val restaurantBridge: RestaurantBridge,
    private val coroutines: UIKitCoroutineDispatcher
) : ViewModel() {

    private val mHomeRestaurantClicked: MutableLiveData<Restaurant> by lazy { MutableLiveData<Restaurant>() }
    val homeRestaurantClicked: LiveData<Restaurant> get() = mHomeRestaurantClicked

    private val mProductOfferClicked: MutableLiveData<UIKitEvent<Unit>> by lazy { MutableLiveData<UIKitEvent<Unit>>() }
    val productOfferClicked: LiveData<UIKitEvent<Unit>> get() = mProductOfferClicked

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
}