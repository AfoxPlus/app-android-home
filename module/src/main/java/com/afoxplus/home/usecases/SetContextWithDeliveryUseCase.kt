package com.afoxplus.home.usecases

import com.afoxplus.home.usecases.actions.SetContextWithDelivery
import com.afoxplus.home.utils.RESTAURANT_NAME
import com.afoxplus.home.utils.RESTAURANT_NO_TABLE
import com.afoxplus.home.utils.RESTAURANT_OWN_DELIVERY
import com.afoxplus.restaurants.entities.Restaurant
import com.afoxplus.restaurants.usecases.actions.SetToContextRestaurant
import com.afoxplus.uikit.objects.vendor.PaymentMethod
import com.afoxplus.uikit.objects.vendor.Vendor
import com.afoxplus.uikit.objects.vendor.VendorShared
import javax.inject.Inject

internal class SetContextWithDeliveryUseCase @Inject constructor(
    private val setToContextRestaurant: SetToContextRestaurant,
    private val vendorShared: VendorShared
) :
    SetContextWithDelivery {

    override fun invoke(restaurant: Restaurant) {
        setToContextRestaurant(restaurant)
        val vendor = Vendor(
            tableId = RESTAURANT_NO_TABLE,
            restaurantId = restaurant.code,
            additionalInfo = mutableMapOf(
                RESTAURANT_NAME to restaurant.name,
                RESTAURANT_OWN_DELIVERY to restaurant.ownDelivery
            ),
            paymentMethod = restaurant.paymentMethods.map {
                PaymentMethod(it.id, it.paymentName, it.isSelected)
            }
        )
        vendorShared.save(vendor)
    }
}