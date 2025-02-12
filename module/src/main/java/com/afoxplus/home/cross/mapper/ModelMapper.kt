package com.afoxplus.home.cross.mapper

import com.afoxplus.home.delivery.views.models.ScanDataModel
import com.afoxplus.home.delivery.views.models.ScanType
import com.afoxplus.places.domain.entities.Establishment
import com.afoxplus.restaurants.entities.RegistrationState
import com.afoxplus.restaurants.entities.Restaurant
import com.afoxplus.uikit.objects.vendor.Vendor

internal fun ScanDataModel.toVendor(): Vendor {
    return Vendor(tableId = this.tableId.toString(), restaurantId = this.restaurantId.toString())
}

internal fun ScanDataModel.toScanType(): ScanType {
    return ScanType.valueOf(this.scanType)
}

internal fun Establishment.toRestaurant(): Restaurant {
    return Restaurant(
        code = this.id,
        name = this.name,
        primaryType = this.primaryType,
        description = this.description,
        urlImageLogo = this.imageLogo,
        urlImageBanner = this.imageBanner,
        ownDelivery = false,
        isOpen = this.isOpen,
        rating = this.rating,
        address = this.address,
        hasSubscription = this.hasSubscription,
        phone = this.phone,
        registrationState = RegistrationState("", ""),
        itemViewType = 1,
        paymentMethods = emptyList(),
    )
}