package com.tsybulnik.gamememory

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            val collectionImages: Preference? = findPreference("PictureCollection");
            val intSquare: Preference? = findPreference("Наборы полей");

            

           collectionImages?.onPreferenceChangeListener = object : Preference.OnPreferenceChangeListener{
               override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
                   collectionImages?.setSummary(newValue.toString())
                   return true
               }
           }
            intSquare?.onPreferenceChangeListener = object : Preference.OnPreferenceChangeListener{
                override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
                    intSquare?.setSummary(newValue.toString())
                    return true
                }
            }

        }

    }
}