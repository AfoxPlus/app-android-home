package com.afoxplus.home.usecases.actions

import com.afoxplus.restaurants.entities.Restaurant

fun interface SetContextWithDelivery {
    operator fun invoke(restaurant: Restaurant)
}