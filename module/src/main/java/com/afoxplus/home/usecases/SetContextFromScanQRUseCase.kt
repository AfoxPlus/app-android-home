package com.afoxplus.home.usecases

import com.afoxplus.home.usecases.actions.SetContextFromScanQR
import com.afoxplus.home.utils.Converts
import com.afoxplus.home.utils.RESTAURANT_NAME
import com.afoxplus.uikit.objects.vendor.PaymentMethod
import com.afoxplus.uikit.objects.vendor.Vendor
import com.afoxplus.uikit.objects.vendor.VendorShared
import javax.inject.Inject

internal class SetContextFromScanQRUseCase @Inject constructor(
    //private val setToContextRestaurantUseCase: FindAndSetToContextRestaurantUseCase,
    private val vendorShared: VendorShared
) : SetContextFromScanQR {

    override suspend fun invoke(data: String) {
        try {
            val vendor = Converts.stringToObject<Vendor>(data)
            /*setToContextRestaurantUseCase(code = vendor.restaurantId).let { restaurant ->
                vendor.additionalInfo = mutableMapOf(RESTAURANT_NAME to restaurant.name)
                vendor.paymentMethod = restaurant.paymentMethods.map {
                    PaymentMethod(it.id, it.paymentName, it.isSelected)
                }
                vendorShared.save(vendor)
            }*/
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}