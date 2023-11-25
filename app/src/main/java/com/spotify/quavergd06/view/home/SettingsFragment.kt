package com.spotify.quavergd06.view.home

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.spotify.quavergd06.R
import com.spotify.quavergd06.model.ThemeManager
import java.util.Locale
import android.content.res.Configuration
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.lifecycleScope
import com.spotify.quavergd06.database.QuaverDatabase
import com.spotify.quavergd06.model.LocaleManager
import com.spotify.quavergd06.view.LoginActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsFragment : PreferenceFragmentCompat() {

    private lateinit var db : QuaverDatabase

    override fun onAttach(context: Context) {
        super.onAttach(context)
        db = QuaverDatabase.getInstance(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val deleteDataButton = view.findViewById<View>(R.id.buttonDeleteData)
        deleteDataButton.setOnClickListener {
            db.clearAllTables()
            clearUserToken()
            requireActivity().finish()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

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

        findPreference<ListPreference>("language_preference")?.setOnPreferenceChangeListener { _, newValue ->
            // Handle language change
            LocaleManager.updateLocale(requireContext(), newValue as String)
            activity?.recreate()
            saveLanguagePreference(newValue as String)
            true
        }

        // Update language entries with localized strings
        val languagePreference = findPreference<ListPreference>("language_preference")
        val languageEntries = resources.getStringArray(R.array.language_entries)
        languagePreference?.entries = languageEntries

    }

    private fun saveLanguagePreference(languageCode: String) {
        val preferences = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        preferences.edit().putString("language_code", languageCode).apply()
    }

    private fun clearUserToken() {
        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
    }

    private fun showDeleteConfirmationDialog() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle(context?.getString(R.string.button_delete_data))
        alertDialogBuilder.setMessage(context?.getString(R.string.delete_confirmation_message))
        alertDialogBuilder.setPositiveButton(context?.getString(R.string.yes)) { dialog, which ->

            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    // Código para realizar operaciones de base de datos
                    db.clearAllTables()
                    clearUserToken()
                    requireActivity().finish()
                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            }
        }
        alertDialogBuilder.setNegativeButton(context?.getString(R.string.no)) { dialog, which ->
            // Usuario hizo clic en No, cerrar el cuadro de diálogo sin hacer nada
            dialog.dismiss()
        }

        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}

