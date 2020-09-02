# Eprefs
[![Release](https://jitpack.io/v/muddassir235/eprefs.svg?style=flat-square)](https://jitpack.io/#muddassir235/eprefs/)

Extended Shared Preferences. Kotlin library that allows you to save and load arrays of primitives and any Serializable data classes.

## Add Dependencies
Add the following in your project level build.gradle
```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```
and the following in your app level build.gradle
```groovy
dependencies {
    implementation 'com.github.muddassir235:eprefs:1.1'
}
```

## How to Use
#### Save to SharedPreferences
Save any boolean, int, long, float, string, or any array of (boolean, int, long, float, string), any Serializable Object, or an ArrayList of any Serializable Objects in SharedPreferences from within a Context (e.g. Activity, Service, Application e.t.c.). Can also be called using `context.save`/`context.load` or `sharedPreferences.save`/`sharedPreferences.load`. (Throws unsupported type exception in case the object is not serializable.)
```kotlin
save("mykey", true)                             // Boolean
save("mykey", 1)                                // Int
save("mykey", 1L)                               // Long
save("mykey", 1.0f)                             // Float
save("mykey", "My String")                      // String
save("mykey", arrayOf("my", "strings"))         // Arrays of all primitive types are supported
.
.
.
save("mykey", anySerializableObject)            // Any Serializable object can be saved.
save("mykey", arrayListOfAnySerializableObject) // An ArrayList of any Serializable Object can also be saved.
```
#### Load from SharedPreferences
Load any saved boolean, int, long, float, string, or array of (boolean, int, long, float, string), a Serializable Object, or an ArrayList of any Serializable Objects from SharedPreferences within a Context. (Throws unsupported type exception in case the object is not serializable.)
```kotlin
load<Boolean>("mykey")
load<Int>("mykey")
load<Long>("mykey")
load<Float>("mykey")
load<String>("mykey")
load<Array<String>>("mykey")                   // Arrays of all primitive types are supported
.
.
.
load<MySerializableObject>("mykey")            // Any Serializable object can be loaded.
load<ArrayList<MySerializableObject>>("mykey") // An ArrayList of any Serializable Object can also be loaded.
```
#### Safe Save and Load
Safe versions of the above save and load functions which avoid/ignore any exceptions.
```kotlin
safeSave("mykey", myObject) // Does nothing if it fails
safeLoad<MyObject>("mykey") // Returns null in case any exception occurs
```

Get a reference to default shared preferences from within a context (e.g. Activity, Service, Application)
```kotlin
this.prefs
```
