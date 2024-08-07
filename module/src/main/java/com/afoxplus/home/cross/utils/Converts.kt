package com.afoxplus.home.cross.utils

import com.google.gson.Gson

object Converts {

    inline fun <reified O> stringToObject(data: String): O {
        return Gson().fromJson(data, O::class.java)
    }

}