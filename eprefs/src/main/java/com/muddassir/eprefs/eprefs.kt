package com.muddassir.eprefs

import android.content.Context
import android.content.SharedPreferences
import java.io.Serializable
import java.lang.Exception
import kotlin.reflect.KClass

/**
 * Get a reference to the default SharedPreferences
 */
val Context.prefs: SharedPreferences get() = this.getSharedPreferences(
    "preferences", Context.MODE_PRIVATE)

/**
 * Context.save - Save any serializable object in SharedPreferences
 *
 * @param key The key of the object to save
 * @param value the value of the object to save
 */
fun Context.save(key: String, value: Any?) {
    prefs.save(key, value)
}

/**
 * Context.safeSave - Save any serializable object in SharedPreferences safely (avoids exceptions)
 *
 * @param key The key of the object to save
 * @param value the value of the object to save
 */
fun Context.safeSave(key: String, value: Any?) {
    prefs.safeSave(key, value)
}

/**
 * Context.load - Load any serializable object in SharedPreferences
 *
 * @param key The key of the object to load
 * @param type The type of the object that is being loaded.
 */
inline fun <reified T: Serializable> Context.load(key: String): T? {
    return prefs.load(key)
}

/**
 * Context.safeLoad - Load any serializable object in SharedPreferences safely (avoids exceptions)
 *
 * @param key The key of the object to load
 * @param type The type of the object that is being loaded.
 */
inline fun <reified T: Serializable> Context.safeLoad(key: String): T? {
    return prefs.safeLoad(key)
}

/**
 * SharedPreferences.safeSave - Save any serializable object in SharedPreferences safely
 *                              (avoids exceptions)
 *
 * @param key The key of the object to save
 * @param value the value of the object to save
 */
fun SharedPreferences.safeSave(key: String, value: Any?) {
    safe { save(key, value) }
}

/**
 * SharedPreferences.safeLoad - Load any serializable object in SharedPreferences safely
 *                              (avoids exceptions)
 *
 * @param key The key of the object to load
 * @template T Serializable type
 */
inline fun <reified T: Serializable> SharedPreferences.safeLoad(key: String): T? {
    return safeLoad(key, T::class)
}

/**
 * SharedPreferences.save - Save any serializable object in SharedPreferences
 *
 * @param key The key of the object to save
 * @param value the value of the object to save
 */
fun SharedPreferences.save(key: String, value: Any?) {
    with (edit()) {
        when (value) {
            is Boolean      -> putBoolean(key, value)
            is Int          -> putInt(key, value)
            is Long         -> putLong(key, value)
            is Float        -> putFloat(key, value)
            is String       -> putString(key, value)
            is Array<*>     -> saveArray(key, value)
            is ArrayList<*> -> saveArrayList(key, value)
            is Serializable -> putString(key, objectToString(value))
            else -> throw Exception("Unsupported Type")
        }

        commit()
    }
}

/**
 * SharedPreferences.load - Load any serializable object in SharedPreferences
 *
 * @param key The key of the object to load
 * @template T Serializable type
 */
inline fun <reified T: Serializable> SharedPreferences.load(key: String): T? {
    return load(key, T::class)
}

fun <T: Any> SharedPreferences.safeLoad(key: String, type: KClass<T>): T? {
    return try {
        load(key, type)
    } catch (e: Exception) {
        null
    }
}

fun <T: Any> SharedPreferences.load(key: String, type: KClass<T>): T? {
    if(!contains(key)) return null

    val result =  when(type) {
        Boolean::class        -> getBoolean(key, false)
        Int::class            -> getInt(key, 0)
        Long::class           -> getLong(key, 0)
        Float::class          -> getFloat(key, 0.0f)
        String::class         -> getString(key, "")
        Array<Boolean>::class -> loadArray(key, Boolean::class)
        Array<Int>::class     -> loadArray(key, Int::class)
        Array<Long>::class    -> loadArray(key, Long::class)
        Array<Float>::class   -> loadArray(key, Float::class)
        Array<String>::class  -> loadArray(key, String::class)
        ArrayList::class      -> loadArrayList(key, Serializable::class)
        Serializable::class   -> stringToObject(getString(key, ""))
        else -> {
            if(type.java.newInstance() is Serializable)  {
                stringToObject(getString(key, ""))
            } else {
                throw Exception("Unsupported Type")
            }
        }
    }

    return result as T
}

private fun SharedPreferences.saveArray(key: String, values: Array<*>) {
    save(key, values.size)

    values.forEachIndexed{ index, it ->
        if(it is Serializable) { save(key+index, it) }
        else throw Exception("Unsupported Type")
    }
}

private fun SharedPreferences.saveArrayList(key: String, values: ArrayList<*>) {
    save(key, values.size)

    values.forEachIndexed{ index, it ->
        if(it is Serializable) { save(key+index, it) }
        else throw Exception("Unsupported Type")
    }
}

private inline fun <reified T: Any> SharedPreferences.loadArray(key: String, type: KClass<T>)
        : Array<T>? {
    val list = ArrayList<T>()

    val length = load(key, Int::class) ?: return null

    for(i in 0 until length) {
        @Suppress("IMPLICIT_CAST_TO_ANY")
        val value = when(type) {
            Boolean::class -> load(key+i, Boolean::class)
            Int::class     -> load(key+i, Int::class)
            Long::class    -> load(key+i, Long::class)
            Float::class   -> load(key+i, Float::class)
            String::class  -> load(key+i, String::class)
            else -> {
                throw Exception("Unsupported Type")
            }
        } as T?

        if(value != null) list.add(value)
    }

    return list.toTypedArray()
}

private fun <T: Any> SharedPreferences.loadArrayList(key: String, type: KClass<T>)
        : ArrayList<T>? {
    val list = ArrayList<T>()

    val length = load(key, Int::class) ?: return null

    for(i in 0 until length) {
        val value = when {
            type == Serializable::class -> load(key + i, type)
            type.java.newInstance() is Serializable -> load(key + i, type)
            else -> {
                throw Exception("Unsupported Type")
            }
        }

        if(value != null) list.add(value)
    }

    return list
}
