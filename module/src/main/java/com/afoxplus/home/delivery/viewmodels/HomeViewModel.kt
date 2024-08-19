package com.afoxplus.home.delivery.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afoxplus.home.cross.mapper.toScanType
import com.afoxplus.home.cross.mapper.toVendor
import com.afoxplus.home.cross.utils.Converts.getScanDataModelFromUri
import com.afoxplus.home.delivery.views.models.ScanDataModel
import com.afoxplus.home.delivery.views.models.ScanType
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

    fun onScanResponse(data: String) {
        viewModelScope.launch(coroutines.getIODispatcher()) {
            try {
                getScanDataModelFromUri(data)?.let { scanDataModel ->
                    when (scanDataModel.toScanType()) {
                        ScanType.TICKET -> mNavigation.emit(Navigation.GoToTicket(scanDataModel))
                        ScanType.VENDOR -> {
                            setRestaurantToCreateOrder(scanDataModel.toVendor())
                            mNavigation.emit(Navigation.GoToMarketOrder)
                        }
                    }
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
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

    fun setRestaurantFromInvitation(restaurantId: String, tableId: String, guestName: String) {
        viewModelScope.launch(coroutines.getMainDispatcher()) {
            setRestaurantToCreateOrder(restaurantId, tableId,guestName)
            mNavigation.emit(Navigation.GoToMarketOrder)
        }
    }

    sealed class Navigation {
        object GoToMarketOrder : Navigation()
        data class GoToTicket(val ticket: ScanDataModel) : Navigation()
    }
}