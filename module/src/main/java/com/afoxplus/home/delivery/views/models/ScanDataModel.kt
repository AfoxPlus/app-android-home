package com.afoxplus.home.delivery.views.models

internal data class ScanDataModel(
    val scanType: String,
    val tableId: String? = null,
    val restaurantId: String? = null,
    val ticketCode: String? = null
)