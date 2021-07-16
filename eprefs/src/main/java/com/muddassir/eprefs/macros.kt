package com.muddassir.eprefs

import android.util.Base64
import com.google.gson.Gson
import java.io.*
import kotlin.reflect.KClass

inline fun safe(action: ((Unit) -> Unit)) {
    try { action.invoke(Unit) } catch (e: Exception) { /* don't care */ }
}

fun objectToString(`object`: Any?): String? {
    val gson = Gson()
    var encoded: String? = null
    try {
        encoded = gson.toJson(`object`).toString()
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return encoded
}

fun <T:Any> stringToObject(string: String?, type: KClass<T>): T? {
    val gson = Gson()
    var `object`: T? = null
    try {
        `object` = gson.fromJson(string, type.java)
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return `object`
}
