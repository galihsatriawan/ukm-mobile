package id.shobrun.ukmmobile.utils

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class SharedPref(val application: Application) {
    companion object {
        private val PREFS_FILENAME = "id.shobrun.stikieventorganizer.prefs"
        val PREFS_USER_USERNAME = "prefs_username"
        val PREFS_USER_EMAIL = "prefs_email"
        val PREFS_USER_ID = "prefs_user_id"
        val PREFS_IS_LOGIN = "prefs_is_login"
        private var prefs: SharedPreferences? = null
    }

    fun <T> getValue(key: String, defaultValue: T): T {
        prefs = prefs ?: application.getSharedPreferences(PREFS_FILENAME, MODE_PRIVATE)
        return when (defaultValue) {
            is String -> {
                prefs!!.getString(key, defaultValue) as T
            }
            is Int -> {
                prefs!!.getInt(key, defaultValue) as T
            }
            else -> {
                prefs!!.getBoolean(key, defaultValue as Boolean) as T
            }
        }
    }

    fun <T> setValue(key: String, value: T) {
        prefs = prefs ?: application.getSharedPreferences(PREFS_FILENAME, MODE_PRIVATE)
        val editor = prefs!!.edit()
        when (value) {
            is String -> {
                editor.putString(key, value)
            }
            is Int -> {
                editor.putInt(key, value)
            }
            else -> {
                editor.putBoolean(key, value as Boolean)
            }
        }
        editor.apply()
    }

    fun removeSharedPref() {
        prefs = prefs ?: application.getSharedPreferences(PREFS_FILENAME, MODE_PRIVATE)
        val editor = prefs!!.edit().clear()
        editor.apply()
    }
}