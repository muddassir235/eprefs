package com.muddassir.eprefs

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import java.io.Serializable
import kotlin.Exception

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class TestPersistence {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun testSaveLoadBool() {
        context.save("test_bool", true)
        assertTrue(context.load("test_bool")?:false)

        context.delete<Boolean>("test_bool")
        assertNull(context.load<Boolean>("test_bool"))
    }

    @Test
    fun testSaveLoadInt() {
        context.save("test_int", 7)
        assertEquals(7,context.load<Int>("test_int")?:0)

        context.delete<Int>("test_int")
        assertNull(context.load<Int>("test_int"))
    }

    @Test
    fun testSaveLoadLong() {
        context.save("test_long", 77777777L)
        assertEquals(77777777L,context.load("test_long")?:0L)

        context.delete<Long>("test_long")
        assertNull(context.load<Long>("test_long"))
    }

    @Test
    fun testSaveLoadFloat() {
        context.save("test_float", 7.7f)
        assertEquals(7.7f,context.load<Float>("test_float")?:0.0f)

        context.delete<Float>("test_float")
        assertNull(context.load<Float>("test_float"))
    }

    @Test
    fun testSaveLoadString() {
        context.save("test_string", "test")
        assertEquals("test",context.load<String>("test_string")?:"")

        context.delete<String>("test_string")
        assertNull(context.load<String>("test_string"))
    }

    @Test
    fun testSaveLoadBooleanArray() {
        val expected = arrayOf(true, true, false)
        context.save("test_bool_array", expected)
        val found = context.load<Array<Boolean>>("test_bool_array") ?: emptyArray()

        assertNotEquals(0, found.size)
        found.forEachIndexed { index, b ->
            assertEquals(expected[index], b)
        }

        context.delete<Array<Boolean>>("test_bool_array")
        assertNull(context.load<Array<Boolean>>("test_bool_array"))
    }

    @Test
    fun testSaveLoadIntArray() {
        val expected = arrayOf(1, 2, 3)
        context.save("test_int_array", expected)
        val found = context.load<Array<Int>>("test_int_array") ?: emptyArray()

        assertNotEquals(0, found.size)
        found.forEachIndexed { index, i ->
            assertEquals(expected[index], i)
        }

        context.delete<Array<Int>>("test_int_array")
        assertNull(context.load<Array<Int>>("test_int_array"))
    }

    @Test
    fun testSaveLoadLongArray() {
        val expected = arrayOf(1L, 2L, 3L)
        context.save("test_long_array", expected)
        val found = context.load<Array<Long>>("test_long_array") ?: emptyArray()

        assertNotEquals(0, found.size)
        found.forEachIndexed { index, l ->
            assertEquals(expected[index], l)
        }

        context.delete<Array<Long>>("test_long_array")
        assertNull(context.load<Array<Long>>("test_long_array"))
    }

    @Test
    fun testSaveLoadFloatArray() {
        val expected = arrayOf(1.0f, 2.0f, 3.0f)
        context.save("test_float_array", expected)
        val found = context.load<Array<Float>>("test_float_array") ?: emptyArray()

        assertNotEquals(0, found.size)
        found.forEachIndexed { index, f ->
            assertEquals(expected[index], f)
        }

        context.delete<Array<Float>>("test_float_array")
        assertNull(context.load<Array<Float>>("test_float_array"))
    }

    @Test
    fun testSaveLoadStringArray() {
        val expected = arrayOf("ready", "set", "go")
        context.save("test_string_array", expected)
        val found = context.load<Array<String>>("test_string_array") ?: emptyArray()

        assertNotEquals(0, found.size)
        found.forEachIndexed { index, s ->
            assertEquals(expected[index], s)
        }

        context.delete<Array<String>>("test_string_array")
        assertNull(context.load<Array<String>>("test_string_array"))
    }

    @Test
    fun testSaveLoadObject() {
        val expected = TestObject(
            false,
            23,
            23L,
            23.0f,
            "Test",
            arrayOf(true, true, true),
            arrayOf(23, 24, 25),
            arrayOf(23L, 24L, 25L),
            arrayOf(23.0f, 24.0f, 25.0f),
            arrayOf("Test1", "Test2", "Test3")
        )
        context.save("test_object", expected)
        val found = context.load("test_object") ?: TestObject()

        assertEquals(expected, found)

        context.delete<TestObject>("test_object")
        assertNull(context.load<TestObject>("test_object"))
    }

    @Test
    fun testSaveLoadObjectArrayList() {
        val expected = ArrayList<TestObject>()
        expected.add(
            TestObject(
                false,
                23,
                23L,
                23.0f,
                "Test",
                arrayOf(true, true, true),
                arrayOf(23, 24, 25),
                arrayOf(23L, 24L, 25L),
                arrayOf(23.0f, 24.0f, 25.0f),
                arrayOf("Test1", "Test2", "Test3")
            )
        )

        expected.add(
            TestObject(
                false,
                33,
                33L,
                33.0f,
                "Test1",
                arrayOf(true, true, true),
                arrayOf(33, 34, 35),
                arrayOf(33L, 34L, 35L),
                arrayOf(33.0f, 34.0f, 35.0f),
                arrayOf("Test10", "Test20", "Test30")
            )
        )

        context.save("test_object_array", expected)
        val found = context.load<ArrayList<TestObject>>("test_object_array")

        found?.toTypedArray()?.forEachIndexed { index, testObject ->
            assertEquals(expected[index], testObject)
        }

        context.delete<ArrayList<TestObject>>("test_object_array")
        assertNull(context.load<ArrayList<TestObject>>("test_object_array"))
    }

    @Test
    fun testSaveLoadObjectArray() {
        val expected = arrayOf(
            TestObject(
                false,
                23,
                23L,
                23.0f,
                "Test",
                arrayOf(true, true, true),
                arrayOf(23, 24, 25),
                arrayOf(23L, 24L, 25L),
                arrayOf(23.0f, 24.0f, 25.0f),
                arrayOf("Test1", "Test2", "Test3")
            ),
            TestObject(
                false,
                33,
                33L,
                33.0f,
                "Test1",
                arrayOf(true, true, true),
                arrayOf(33, 34, 35),
                arrayOf(33L, 34L, 35L),
                arrayOf(33.0f, 34.0f, 35.0f),
                arrayOf("Test10", "Test20", "Test30")
            )
        )
        context.save("test_object_array", expected)
        val found = context.load<ArrayList<TestObject>>("test_object_array")

        found?.toTypedArray()?.forEachIndexed { index, testObject ->
            assertEquals(expected[index], testObject)
        }

        context.delete<ArrayList<TestObject>>("test_object_array")
        assertNull(context.load<ArrayList<TestObject>>("test_object_array"))
    }

    @Test
    fun testLoadNonExistentObject() {
        assertNull(context.load<Boolean>("nonexistent_key"))
        assertNull(context.load<Int>("nonexistent_key"))
        assertNull(context.load<Long>("nonexistent_key"))
        assertNull(context.load<Float>("nonexistent_key"))
        assertNull(context.load<String>("nonexistent_key"))
        assertNull(context.load<Array<Boolean>>("nonexistent_key"))
        assertNull(context.load<Array<Int>>("nonexistent_key"))
        assertNull(context.load<Array<Long>>("nonexistent_key"))
        assertNull(context.load<Array<Float>>("nonexistent_key"))
        assertNull(context.load<Array<String>>("nonexistent_key"))
        assertNull(context.load<Serializable>("nonexistent_key"))
        assertNull(context.load<TestObject>("nonexistent_key"))
        assertNull(context.load<Array<TestObject>>("nonexistent_key"))
        assertNull(context.load<ArrayList<TestObject>>("nonexistent_key"))
    }

    @Test(expected = Exception::class)
    fun testUnsupportedObjectSave() {
        context.save("exception_key", UnsupportedTestObject())
    }

    @Test(expected = Exception::class)
    fun testUnsupportedArraySave() {
        context.save("exception_key", arrayOf(UnsupportedTestObject()))
    }

    @Test(expected = Exception::class)
    fun testUnsupportedArrayListSave() {
        val list = ArrayList<UnsupportedTestObject>()
        list.add(UnsupportedTestObject())
        context.save("exception_key", list)
    }

    @Test
    fun testUnsupportedObjectSaveSafe() {
        context.safeSave("exception_key", UnsupportedTestObject())
    }

    @Test
    fun testUnsupportedArraySaveSafe() {
        context.safeSave("exception_key", arrayOf(UnsupportedTestObject()))
    }

    @Test
    fun testUnsupportedObjectListSaveSafe() {
        val list = ArrayList<UnsupportedTestObject>()
        list.add(UnsupportedTestObject())
        context.safeSave("exception_key", list)
    }
}