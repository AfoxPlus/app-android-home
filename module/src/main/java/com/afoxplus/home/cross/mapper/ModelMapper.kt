package com.afoxplus.home.cross.mapper

import com.afoxplus.home.delivery.views.models.ScanDataModel
import com.afoxplus.home.delivery.views.models.ScanType
import com.afoxplus.uikit.objects.vendor.Vendor

internal fun ScanDataModel.toVendor(): Vendor {
    return Vendor(tableId = this.tableId.toString(), restaurantId = this.restaurantId.toString())
}

internal fun ScanDataModel.toScanType(): ScanType {
    return ScanType.valueOf(this.scanType)
}