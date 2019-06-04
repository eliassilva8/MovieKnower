package com.ems.movieknower.Preferences

import android.os.Bundle
import android.support.v7.preference.EditTextPreference
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceFragmentCompat
import com.ems.movieknower.R

class PreferencesFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        val yearPref: EditTextPreference = findPreference(getString(R.string.preference_year)) as EditTextPreference
        yearPref.onPreferenceChangeListener = object : Preference.OnPreferenceChangeListener {
            override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
                val pref = preference as EditTextPreference
                val value = newValue as String

                if (value.isEmpty() || value.isBlank() || value.any { it.isLetter() }) {
                    pref.summary = getString(R.string.all_time)
                } else {
                    pref.summary = newValue
                }
                return true
            }
        }
    }
}