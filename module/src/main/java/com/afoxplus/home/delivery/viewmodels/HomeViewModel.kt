package com.afoxplus.home.delivery.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afoxplus.products.delivery.views.events.OnClickProductOfferEvent
import com.afoxplus.restaurants.delivery.flow.RestaurantBridge
import com.afoxplus.restaurants.entities.Restaurant
import com.afoxplus.uikit.bus.Event
import com.afoxplus.uikit.bus.EventBusListener
import com.afoxplus.uikit.di.UIKitMainDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productEventBus: EventBusListener,
    private val restaurantBridge: RestaurantBridge,
    @UIKitMainDispatcher private val dispatcherMain: CoroutineDispatcher
) : ViewModel() {

    private val mHomeRestaurantClicked: MutableLiveData<Restaurant> by lazy { MutableLiveData<Restaurant>() }
    val homeRestaurantClicked: LiveData<Restaurant> get() = mHomeRestaurantClicked

    private val mProductOfferClicked: MutableLiveData<Event<Unit>> by lazy { MutableLiveData<Event<Unit>>() }
    val productOfferClicked: LiveData<Event<Unit>> get() = mProductOfferClicked

    init {
        viewModelScope.launch(dispatcherMain) {
            restaurantBridge.fetchRestaurant().observeForever { restaurant ->
                mHomeRestaurantClicked.postValue(restaurant)
            }
        }

        viewModelScope.launch(dispatcherMain) {
            productEventBus.subscribe().collectLatest { event ->
                if (event is OnClickProductOfferEvent) {
                    mProductOfferClicked.postValue(Event(Unit))
                }
            }
        }
    }
}