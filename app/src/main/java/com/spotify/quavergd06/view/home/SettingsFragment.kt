package com.spotify.quavergd06.view.home

import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.spotify.quavergd06.R
import com.spotify.quavergd06.model.ThemeManager

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        findPreference<ListPreference>("theme_preference")?.setOnPreferenceChangeListener { _, newValue ->
            // Handle theme change
            val selectedTheme = newValue as String
            ThemeManager.saveThemePreference(requireContext(), selectedTheme)
            ThemeManager.applyTheme(requireContext())
            activity?.recreate()
            true
        }

        // Update theme entries with localized strings
        val themePreference = findPreference<ListPreference>("theme_preference")
        val themeEntries = resources.getStringArray(R.array.theme_entries)
        themePreference?.entries = themeEntries
    }

}

