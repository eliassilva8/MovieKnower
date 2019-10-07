package com.ems.movieknower

import android.os.Bundle
import android.text.TextUtils
import androidx.preference.EditTextPreference
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

class PreferencesFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        val mainActivity = activity as MainActivity
        mainActivity.supportActionBar!!.title = getString(R.string.action_settings)

        val ratingPref: ListPreference? = findPreference(getString(R.string.preference_rating))
        ratingPref?.title = ratingPref?.title.toString().plus(":")

        val voteCountPref: EditTextPreference? = findPreference(getString(R.string.preference_vote_count))
        voteCountPref?.summaryProvider = Preference.SummaryProvider<EditTextPreference> { preference ->
            val text = preference.text
            if (TextUtils.isEmpty(text) || text.any { it.isLetter() }) {
                resources.getString(R.string.all)
            } else {
                text
            }
        }

        val yearPref: EditTextPreference? = findPreference(getString(R.string.preference_year))
        yearPref?.summaryProvider = Preference.SummaryProvider<EditTextPreference> { preference ->
            val text = preference.text
            if (TextUtils.isEmpty(text) || text.any { it.isLetter() }) {
                resources.getString(R.string.all_time)
            } else {
                text
            }
        }
    }
}