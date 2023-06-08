package com.afoxplus.home.usecases.actions

internal fun interface SetDataToContext {
    suspend operator fun invoke(data: String)
}