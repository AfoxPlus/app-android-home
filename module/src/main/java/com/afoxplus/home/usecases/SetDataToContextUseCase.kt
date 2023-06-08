package com.afoxplus.home.usecases

import com.afoxplus.home.usecases.actions.SetDataToContext
import com.afoxplus.home.utils.Converts
import com.afoxplus.restaurants.usecases.FindAndSetToContextRestaurantUseCase
import com.afoxplus.uikit.objects.vendor.Vendor
import com.afoxplus.uikit.objects.vendor.VendorShared
import javax.inject.Inject

internal class SetDataToContextUseCase @Inject constructor(
    private val setToContextRestaurantUseCase: FindAndSetToContextRestaurantUseCase,
    private val vendorShared: VendorShared
) : SetDataToContext {

    override suspend fun invoke(data: String) {
        try {
            val vendor = Converts.stringToObject<Vendor>(data)
            setToContextRestaurantUseCase(code = vendor.restaurantId).let { restaurant ->
                vendor.additionalInfo[RESTAURANT_NAME] = restaurant.name
                vendorShared.save(vendor)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    companion object {
        private const val RESTAURANT_NAME = "restaurant_name"
    }
}