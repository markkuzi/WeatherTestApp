package com.example.data.storage

import android.content.Context

interface SharedPrefsCityStorage : Storage<String> {

    class Base(context: Context) : SharedPrefsCityStorage {

        private val sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)

        override fun save(source: String) {
            sharedPreferences.edit().putString(KEY_CITY, source).apply()
        }

        override fun load(): String {
            return sharedPreferences.getString(KEY_CITY, DEFAULT_CITY) ?: DEFAULT_CITY
        }
    }

    companion object {
        private const val SHARED_PREFS_NAME = "name"
        private const val KEY_CITY = "city"
        private const val DEFAULT_CITY = "Saint Petersburg"
    }
}