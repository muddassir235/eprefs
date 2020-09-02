package com.muddassir.eprefs

import android.util.Base64
import java.io.*

inline fun safe(action: ((Unit) -> Unit)) {
    try { action.invoke(Unit) } catch (e: Exception) { /* don't care */ }
}

internal fun objectToString(`object`: Serializable?): String? {
    var encoded: String? = null
    try {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val objectOutputStream = ObjectOutputStream(byteArrayOutputStream)
        objectOutputStream.writeObject(`object`)
        objectOutputStream.close()
        encoded = Base64.encodeToString(byteArrayOutputStream.toByteArray(), 0)
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return encoded
}

fun stringToObject(string: String?): Serializable? {
    val bytes = Base64.decode(string, 0)
    var `object`: Serializable? = null
    try {
        val objectInputStream =
            ObjectInputStream(ByteArrayInputStream(bytes))
        `object` = objectInputStream.readObject() as Serializable
    } catch (e: IOException) {
        e.printStackTrace()
    } catch (e: ClassNotFoundException) {
        e.printStackTrace()
    } catch (e: ClassCastException) {
        e.printStackTrace()
    }
    return `object`
}
