package com.muddassir.eprefs

import android.content.Context
import android.content.SharedPreferences
import java.lang.Exception
import kotlin.reflect.KClass

/**
 * Get a reference to the default SharedPreferences
 */
val Context.prefs: SharedPreferences get() = this.getSharedPreferences(
    "preferences", Context.MODE_PRIVATE)

/**
 * Context.save - Save any data object in SharedPreferences
 *
 * @param key The key of the object to save
 * @param value the value of the object to save
 */
fun Context.save(key: String, value: Any) {
    prefs.save(key, value)
}

/**
 * Context.safeSave - Save any data object in SharedPreferences safely (avoids exceptions)
 *
 * @param key The key of the object to save
 * @param value the value of the object to save
 */
fun Context.safeSave(key: String, value: Any) {
    prefs.safeSave(key, value)
}

/**
 * Context.load - Load any data object in SharedPreferences
 *
 * @param key The key of the object to load
 */
inline fun <reified T: Any> Context.load(key: String): T? {
    return prefs.load(key)
}

/**
 * Context.safeLoad - Load any data object in SharedPreferences safely (avoids exceptions)
 *
 * @param key The key of the object to load
 */
inline fun <reified T: Any> Context.safeLoad(key: String): T? {
    return prefs.safeLoad(key)
}

/**
 * Context.load - Delete any object in SharedPreferences
 *
 * @param key The key of the object to delete
 */
inline fun <reified T: Any> Context.delete(key: String) {
    return prefs.delete<T>(key)
}

/**
 * Context.safeLoad - Delete any object in SharedPreferences safely (avoids exceptions)
 *
 * @param key The key of the object to delete
 */
inline fun <reified T: Any> Context.safeDelete(key: String) {
    return prefs.safeDelete<T>(key)
}

/**
 * SharedPreferences.safeSave - Save any data object in SharedPreferences safely
 *                              (avoids exceptions)
 *
 * @param key The key of the object to save
 * @param value the value of the object to save
 */
fun SharedPreferences.safeSave(key: String, value: Any) {
    safe { save(key, value) }
}

/**
 * SharedPreferences.safeLoad - Load any data object in SharedPreferences safely
 *                              (avoids exceptions)
 *
 * @param key The key of the object to load
 * @template T Any type
 */
inline fun <reified T: Any> SharedPreferences.safeLoad(key: String): T? {
    return safeLoad(key, T::class)
}

/**
 * SharedPreferences.save - Save any any object in SharedPreferences
 *
 * @param key The key of the object to save
 * @param value the value of the object to save
 */
fun SharedPreferences.save(key: String, value: Any) {
    with (edit()) {
        when (value) {
            is Boolean      -> putBoolean(key, value)
            is Int          -> putInt(key, value)
            is Long         -> putLong(key, value)
            is Float        -> putFloat(key, value)
            is String       -> putString(key, value)
            else -> putString(key, objectToString(value))
        }

        commit()
    }
}

/**
 * SharedPreferences.delete - Delete any  object that is saved in SharedPreferences safely
 *                            avoiding any exceptions
 *
 * @param key The key of the object to delete
 * @template T Any type
 */
inline fun <reified T: Any> SharedPreferences.safeDelete(key: String) {
    try {
        delete<T>(key)
    } catch (e: Exception) {
        // Do nothing
    }
}

/**
 * SharedPreferences.delete - Delete any  object that is saved in SharedPreferences
 *
 * @param key The key of the object to delete
 * @template T Any type
 */
inline fun <reified T: Any> SharedPreferences.delete(key: String) {
    return delete(key, T::class)
}

fun <T: Any> SharedPreferences.delete(key: String, type: KClass<T>) {
    val editor = this.edit()

    when(type) {
        Boolean::class        -> editor.remove(key)
        Int::class            -> editor.remove(key)
        Long::class           -> editor.remove(key)
        Float::class          -> editor.remove(key)
        String::class         -> editor.remove(key)
        else -> editor.remove(key)
    }

    editor.apply()
}

/**
 * SharedPreferences.load - Load any any object in SharedPreferences
 *
 * @param key The key of the object to load
 * @template T any type
 */
inline fun <reified T: Any> SharedPreferences.load(key: String): T? {
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
        else -> stringToObject(getString(key, ""), type)
    }

    return result as T?
}
