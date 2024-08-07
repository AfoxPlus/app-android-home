package com.afoxplus.home.domain.usecases

import com.afoxplus.home.domain.entities.RestaurantOrderType
import com.afoxplus.home.cross.utils.Converts
import com.afoxplus.home.cross.utils.RESTAURANT_NAME
import com.afoxplus.home.cross.utils.RESTAURANT_NO_TABLE
import com.afoxplus.home.cross.utils.RESTAURANT_ORDER_TYPE
import com.afoxplus.home.cross.utils.RESTAURANT_OWN_DELIVERY
import com.afoxplus.restaurants.entities.Restaurant
import com.afoxplus.restaurants.usecases.FindAndSetToContextRestaurantUseCase
import com.afoxplus.restaurants.usecases.actions.SetToContextRestaurant
import com.afoxplus.uikit.objects.vendor.PaymentMethod
import com.afoxplus.uikit.objects.vendor.Vendor
import com.afoxplus.uikit.objects.vendor.VendorShared
import javax.inject.Inject

internal class SetRestaurantToCreateOrder @Inject constructor(
    private val setToContextRestaurant: SetToContextRestaurant,
    private val findRestaurantAndSetToContext: FindAndSetToContextRestaurantUseCase,
    private val vendorShared: VendorShared
) {

    operator fun invoke(restaurant: Restaurant, orderType: RestaurantOrderType) {
        setToContextRestaurant(restaurant)
        val vendor = Vendor(
            tableId = RESTAURANT_NO_TABLE,
            restaurantId = restaurant.code,
            additionalInfo = mutableMapOf(
                RESTAURANT_NAME to restaurant.name,
                RESTAURANT_OWN_DELIVERY to restaurant.ownDelivery,
                RESTAURANT_ORDER_TYPE to orderType.name
            ),
            paymentMethod = restaurant.paymentMethods.map {
                PaymentMethod(it.id, it.paymentName, it.isSelected)
            }
        )
        vendorShared.save(vendor)
    }

    suspend operator fun invoke(scanData: String) {
        try {
            val vendor = Converts.stringToObject<Vendor>(scanData)
            findRestaurantAndSetToContext(code = vendor.restaurantId).let { restaurant ->
                vendor.additionalInfo = mutableMapOf(RESTAURANT_NAME to restaurant.name)
                vendor.paymentMethod = restaurant.paymentMethods.map {
                    PaymentMethod(it.id, it.paymentName, it.isSelected)
                }
                vendorShared.save(vendor)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}