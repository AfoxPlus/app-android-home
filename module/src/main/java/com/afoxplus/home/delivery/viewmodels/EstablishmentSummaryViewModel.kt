package com.afoxplus.home.delivery.viewmodels

import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afoxplus.home.delivery.model.DeeplinkRoute
import com.afoxplus.home.domain.usecases.SetRestaurantToCreateOrder
import com.afoxplus.uikit.bus.UIKitEventBusWrapper
import com.afoxplus.uikit.di.UIKitCoroutineDispatcher
import com.afoxplus.uikit.objects.vendor.Vendor
import com.afoxplus.uikit.objects.vendor.VendorShared
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class EstablishmentSummaryViewModel @Inject constructor(
    private val eventBusListener: UIKitEventBusWrapper,
    private val vendorShared: VendorShared,
    private val setRestaurantToCreateOrder: SetRestaurantToCreateOrder,
    private val dispatcher: UIKitCoroutineDispatcher
) :
    ViewModel() {

    private val mEstablishmentCodeState: MutableStateFlow<String> by lazy { MutableStateFlow("") }
    val establishmentCodeState = mEstablishmentCodeState.asStateFlow()

    private val mNavigation: MutableSharedFlow<Navigation> by lazy { MutableSharedFlow() }
    val navigation = mNavigation.asSharedFlow()

    val onEventBusListener = eventBusListener.listen()

    init {
        vendorShared.fetch()?.let {
            mEstablishmentCodeState.value = it.restaurantId
        }
    }

    fun handlerDeeplinkEvent(deeplink: String) {
        viewModelScope.launch(dispatcher.getMainDispatcher()) {
            val uri = deeplink.toUri()
            val path = uri.path
            val params = path?.split("/")
            when (DeeplinkRoute.toRoute(params?.get(1))) {
                DeeplinkRoute.Menu,
                DeeplinkRoute.ProductDetail -> {
                    setRestaurantToCreateOrder(
                        Vendor(
                            tableId = "-",
                            restaurantId = params?.get(2).orEmpty()
                        )
                    )
                    mNavigation.emit(Navigation.GoToMarketOrder)
                }

                DeeplinkRoute.PhotoDetail -> {

                }

                else -> {

                }
            }

        }
    }


    sealed class Navigation {
        object GoToMarketOrder : Navigation()
    }
}