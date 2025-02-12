package com.afoxplus.home.delivery.viewmodels

import androidx.lifecycle.ViewModel
import com.afoxplus.home.cross.mapper.toRestaurant
import com.afoxplus.home.domain.entities.RestaurantOrderType
import com.afoxplus.home.domain.usecases.SetRestaurantToCreateOrder
import com.afoxplus.places.domain.entities.Establishment
import com.afoxplus.uikit.bus.UIKitEventBusWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class EstablishmentPlaceViewModel @Inject constructor(
    private val setRestaurantToCreateOrder: SetRestaurantToCreateOrder,

    private val eventBusListener: UIKitEventBusWrapper
) : ViewModel() {
    val onEventBusListener = eventBusListener.listen()

    fun setEstablishment(establishment: Establishment) {
        setRestaurantToCreateOrder(establishment.toRestaurant(), RestaurantOrderType.TABLE)
    }
}