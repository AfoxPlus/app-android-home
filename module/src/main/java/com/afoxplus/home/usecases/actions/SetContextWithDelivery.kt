package com.afoxplus.home.usecases.actions

import com.afoxplus.restaurants.delivery.models.RestaurantEventModel

fun interface SetContextWithDelivery {
    operator fun invoke(restaurant: RestaurantEventModel)
}