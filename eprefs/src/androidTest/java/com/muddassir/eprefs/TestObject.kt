package com.muddassir.eprefs

import java.io.Serializable

data class TestObject(
    val boolean: Boolean = true,
    val int: Int = 0,
    val long: Long = 0L,
    val float: Float = 1.0f,
    val string: String = "",
    val booleanArray: Array<Boolean> = arrayOf(false, false, false),
    val intArray: Array<Int> = arrayOf(1, 2, 3),
    val longArray: Array<Long> = arrayOf(1L, 2L, 3L),
    val floatArray: Array<Float> = arrayOf(1.0f, 2.0f, 3.0f),
    val stringArray: Array<String> = arrayOf("1", "2", "3")
): Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TestObject

        if (boolean != other.boolean) return false
        if (int != other.int) return false
        if (long != other.long) return false
        if (float != other.float) return false
        if (string != other.string) return false
        if (!booleanArray.contentEquals(other.booleanArray)) return false
        if (!intArray.contentEquals(other.intArray)) return false
        if (!longArray.contentEquals(other.longArray)) return false
        if (!floatArray.contentEquals(other.floatArray)) return false
        if (!stringArray.contentEquals(other.stringArray)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = boolean.hashCode()
        result = 31 * result + int
        result = 31 * result + long.hashCode()
        result = 31 * result + float.hashCode()
        result = 31 * result + string.hashCode()
        result = 31 * result + booleanArray.contentHashCode()
        result = 31 * result + intArray.contentHashCode()
        result = 31 * result + longArray.contentHashCode()
        result = 31 * result + floatArray.contentHashCode()
        result = 31 * result + stringArray.contentHashCode()
        return result
    }
}

data class UnsupportedTestObject(
    val boolean: Boolean = true
)