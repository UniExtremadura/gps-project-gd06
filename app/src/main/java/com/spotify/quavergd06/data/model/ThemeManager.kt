package com.spotify.quavergd06.model
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import com.spotify.quavergd06.R
object ThemeManager {

    private const val THEME_PREFERENCE_KEY = "theme_preference"

    fun applyTheme(context: Context) {
        val preferences = getPreferences(context)

        // Set the theme
        when (preferences.getString(THEME_PREFERENCE_KEY, "light_theme")) {
            "light_theme" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            "dark_theme" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }


    fun saveThemePreference(context: Context, theme: String) {
        val preferences = getPreferences(context)
        preferences.edit().putString(THEME_PREFERENCE_KEY, theme).apply()
    }

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    }
}