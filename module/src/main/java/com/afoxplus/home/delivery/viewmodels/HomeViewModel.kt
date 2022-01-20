package com.afoxplus.home.delivery.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afoxplus.products.delivery.views.events.OnClickProductOfferEvent
import com.afoxplus.restaurants.delivery.views.events.OnClickRestaurantHomeEvent
import com.afoxplus.uikit.bus.Event
import com.afoxplus.uikit.bus.EventBusListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productEventBus: EventBusListener
) : ViewModel() {

    private val mHomeRestaurantClicked: MutableLiveData<Event<Unit>> by lazy { MutableLiveData<Event<Unit>>() }
    val homeRestaurantClicked: LiveData<Event<Unit>> get() = mHomeRestaurantClicked

    private val mProductOfferClicked: MutableLiveData<Event<Unit>> by lazy { MutableLiveData<Event<Unit>>() }
    val productOfferClicked: LiveData<Event<Unit>> get() = mProductOfferClicked

    init {
        viewModelScope.launch(Dispatchers.Main) {
            productEventBus.subscribe().collectLatest { event ->
                if (event is OnClickRestaurantHomeEvent) {
                    mHomeRestaurantClicked.postValue(Event(Unit))
                }
            }
        }

        viewModelScope.launch(Dispatchers.Main) {
            productEventBus.subscribe().collectLatest { event ->
                if (event is OnClickProductOfferEvent) {
                    mProductOfferClicked.postValue(Event(Unit))
                }
            }
        }
    }
}