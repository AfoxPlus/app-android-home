package com.afoxplus.home.cross.utils

import android.net.Uri
import com.afoxplus.home.delivery.views.models.ScanDataModel
import com.afoxplus.home.delivery.views.models.ScanType
import com.google.gson.Gson

internal object Converts {

    inline fun <reified O> stringToObject(data: String): O {
        return Gson().fromJson(data, O::class.java)
    }

    fun getScanDataModelFromUri(data: String): ScanDataModel? {
        try {
            val uri = Uri.parse(data)
            val path = uri.path
            val params = path?.split("/")
            val scanType = params?.get(2)
            scanType?.let { type ->
                var tableId: String? = null
                var restaurantId: String? = null
                var ticketCode: String? = null
                when (type) {
                    ScanType.TICKET.name -> {
                        ticketCode = params[3]
                    }

                    ScanType.VENDOR.name -> {
                        tableId = params[3]
                        restaurantId = params[4]
                    }
                }
                return ScanDataModel(
                    scanType = type,
                    restaurantId = restaurantId,
                    tableId = tableId,
                    ticketCode = ticketCode
                )
            } ?: return null
        } catch (ex: Exception) {
            return null
        }
    }

}