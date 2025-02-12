package com.afoxplus.home.delivery.model

internal enum class DeeplinkRoute(val route: String) {
    Menu("menu"),
    ProductDetail("product"),
    PhotoDetail("photos"),
    Places("places");

    companion object {
        fun toRoute(route: String?): DeeplinkRoute? {
           return DeeplinkRoute.values().find { item -> item.route == route }
        }
    }
}