package com.ems.movieknower

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.preference.PreferenceManager
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import com.ems.movieknower.Preferences.PreferencesActivity
import com.ems.movieknower.data.ApiCall
import com.ems.movieknower.data.apiKey
import com.ems.movieknower.data.themoviedbKey
import com.ems.movieknower.databinding.MoviesListActivityBinding


class MoviesListActivity: AppCompatActivity() {
    val num_columns = 2
    lateinit var searchView: SearchView
    lateinit var binding: MoviesListActivityBinding
    lateinit var apiCall: ApiCall
    lateinit var prefsMap: HashMap<String, String>
    lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.movies_list_activity)
        setSupportActionBar(binding.moviesListToolbar)

        setUpRecyclerView(binding)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        refreshMoviesData()
    }

    override fun onResume() {
        super.onResume()
        refreshMoviesData()
    }

    private fun refreshMoviesData() {
        val sortBy = sharedPreferences.getString(getString(R.string.preference_sort_by), "popularity.desc")
        val rating = sharedPreferences.getString(getString(R.string.preference_rating), "")
        val year = sharedPreferences.getString(getString(R.string.preference_year), "")
        val voteCount = sharedPreferences.getString(getString(R.string.preference_vote_count), "1000")

        prefsMap = hashMapOf<String, String>()
        prefsMap.put(apiKey, themoviedbKey)
        prefsMap.put("sort_by", sortBy)
        prefsMap.put("vote_average.gte", rating)
        prefsMap.put("year", year)
        prefsMap.put("vote_count.gte", voteCount)

        apiCall = ApiCall(binding)
        apiCall.moviePref(prefsMap)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.menu_search).getActionView() as SearchView
        searchView.setSearchableInfo(
            searchManager.getSearchableInfo(componentName)
        )
        searchView.maxWidth = Integer.MAX_VALUE
        searchMovie()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_restore_preferences -> restorePreferences()
            R.id.menu_filter -> {
                val intent = Intent(this, PreferencesActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun restorePreferences() {
        sharedPreferences.edit().putString(getString(R.string.preference_sort_by), "popularity.desc").apply()
        sharedPreferences.edit().putString(getString(R.string.preference_rating), "").apply()
        sharedPreferences.edit().putString(getString(R.string.preference_year), "").apply()
        sharedPreferences.edit().putString(getString(R.string.preference_vote_count), "1000").apply()

        refreshMoviesData()
    }

    private fun searchMovie() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                closeKeyboard()
                apiCall = ApiCall(binding)
                apiCall.movieByName(query)
                searchView.onActionViewCollapsed()
                return true
            }
        })
    }

    private fun closeKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm : InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun setUpRecyclerView(binding: MoviesListActivityBinding) {
        val layoutManager = GridLayoutManager(binding.moviesGrid.context, num_columns, GridLayoutManager.VERTICAL, false)
        binding.moviesGrid.layoutManager = layoutManager
        binding.moviesGrid.setHasFixedSize(true)
    }
}