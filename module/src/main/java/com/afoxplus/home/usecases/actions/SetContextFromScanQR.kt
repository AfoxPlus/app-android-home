package com.afoxplus.home.usecases.actions

internal fun interface SetContextFromScanQR {
    suspend operator fun invoke(data: String)
}