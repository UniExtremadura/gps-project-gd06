package com.spotify.quavergd06.view.home

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.spotify.quavergd06.R
import com.spotify.quavergd06.model.ThemeManager
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.preference.Preference
import com.spotify.quavergd06.database.QuaverDatabase
import com.spotify.quavergd06.databinding.FragmentSettingsBinding
import com.spotify.quavergd06.model.LocaleManager
import com.spotify.quavergd06.util.AppContainer
import com.spotify.quavergd06.view.LoginActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsFragment : PreferenceFragmentCompat() {

    private var _binding: FragmentSettingsBinding? = null

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        findPreference<ListPreference>("theme_preference")?.setOnPreferenceChangeListener { _, newValue ->
            // Handle theme change
            val selectedTheme = newValue as String
            handleThemeChange(selectedTheme)
            true
        }


        findPreference<ListPreference>("language_preference")?.setOnPreferenceChangeListener { _, newValue ->
            // Handle language change
            val selectedLanguage = newValue as String
            handleLocaleChange(selectedLanguage)
            true
        }

        findPreference<Preference>("delete_data_preference")?.setOnPreferenceClickListener {
            showDeleteConfirmationDialog()
            true
        }

        // Update theme entries with localized strings
        val themePreference = findPreference<ListPreference>("theme_preference")
        val themeEntries = resources.getStringArray(R.array.theme_entries)
        themePreference?.entries = themeEntries

        // Update language entries with localized strings
        val languagePreference = findPreference<ListPreference>("language_preference")
        val languageEntries = resources.getStringArray(R.array.language_entries)
        languagePreference?.entries = languageEntries

    }

    private fun handleLocaleChange(selectedLanguage: String) {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle(context?.getString(R.string.button_change_language))
        alertDialogBuilder.setMessage(context?.getString(R.string.change_language_confirmation_message))
        alertDialogBuilder.setPositiveButton(context?.getString(R.string.yes)) { _, _ ->
            LocaleManager.updateLocale(requireContext(), selectedLanguage)
            saveLanguagePreference(selectedLanguage)
            // reiniciar la aplicacion
            restartApplication()
        }
        alertDialogBuilder.setNegativeButton(context?.getString(R.string.no)) { dialog, which ->
            // Usuario hizo clic en No, cerrar el cuadro de diálogo sin hacer nada
            dialog.dismiss()
        }

        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()

    }

    private fun handleThemeChange(selectedTheme: String) {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle(context?.getString(R.string.button_change_theme))
        alertDialogBuilder.setMessage(context?.getString(R.string.change_theme_confirmation_message))
        alertDialogBuilder.setPositiveButton(context?.getString(R.string.yes)) { dialog, which ->
            ThemeManager.saveThemePreference(requireContext(), selectedTheme)
            ThemeManager.applyTheme(requireContext())
            // reiniciar la aplicacion
            restartApplication()
        }
        alertDialogBuilder.setNegativeButton(context?.getString(R.string.no)) { dialog, which ->
            // Usuario hizo clic en No, cerrar el cuadro de diálogo sin hacer nada
            dialog.dismiss()
        }

        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()

    }

    private fun restartApplication() {
        val intent = requireActivity().baseContext.packageManager.getLaunchIntentForPackage(requireActivity().baseContext.packageName)
        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        requireActivity().finishAffinity()
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
                    AppContainer(requireContext()).clearAll()
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
