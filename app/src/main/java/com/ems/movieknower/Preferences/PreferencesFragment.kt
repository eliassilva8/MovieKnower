package com.ems.movieknower.Preferences

import android.os.Bundle
import android.text.TextUtils
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.ems.movieknower.R

class PreferencesFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        val voteCountPref: EditTextPreference? = findPreference(getString(R.string.preference_vote_count))
        voteCountPref?.summaryProvider = Preference.SummaryProvider<EditTextPreference> { preference ->
            val text = preference.text
            if (TextUtils.isEmpty(text) || text.any { it.isLetter() }) {
                "All"
            } else {
                text
            }
        }

        val yearPref: EditTextPreference? = findPreference(getString(R.string.preference_year))
        yearPref?.summaryProvider = Preference.SummaryProvider<EditTextPreference> { preference ->
            val text = preference.text
            if (TextUtils.isEmpty(text) || text.any { it.isLetter() }) {
                "All Time"
            } else {
                text
            }
        }
    }
}