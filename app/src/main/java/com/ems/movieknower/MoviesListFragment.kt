package com.ems.movieknower


import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import com.ems.movieknower.data.ApiCall
import com.ems.movieknower.data.apiKey
import com.ems.movieknower.data.themoviedbKey
import com.ems.movieknower.databinding.FragmentMoviesListBinding
import com.ems.movieknower.preferences.PreferencesActivity
import java.util.*

class MoviesListFragment : Fragment(), SharedPreferences.OnSharedPreferenceChangeListener {
    val num_columns = 2
    lateinit var searchView: SearchView
    lateinit var binding: FragmentMoviesListBinding
    lateinit var apiCall: ApiCall
    lateinit var prefsMap: HashMap<String, String?>
    lateinit var sharedPreferences: SharedPreferences
    lateinit var systemLanguage: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies_list, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        systemLanguage = Locale.getDefault().language
        setUpRecyclerView(binding)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        refreshMoviesData()
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        refreshMoviesData()
    }

    private fun refreshMoviesData() {
        val sortBy =
            sharedPreferences.getString(getString(R.string.preference_sort_by), "popularity.desc")
        val rating = sharedPreferences.getString(getString(R.string.preference_rating), "0")
        val year = sharedPreferences.getString(getString(R.string.preference_year), "")
        val voteCount =
            sharedPreferences.getString(getString(R.string.preference_vote_count), "1000")

        prefsMap = hashMapOf<String, String?>()
        prefsMap.put(apiKey, themoviedbKey)
        prefsMap.put("sort_by", sortBy)
        prefsMap.put("vote_average.gte", rating)
        prefsMap.put("primary_release_year", year)
        prefsMap.put("vote_count.gte", voteCount)
        prefsMap.put("language", systemLanguage)

        apiCall = ApiCall(binding)
        apiCall.moviePref(prefsMap)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)

        val searchManager = activity!!.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.menu_search).actionView as SearchView
        searchView.setSearchableInfo(
            searchManager.getSearchableInfo(activity!!.componentName)
        )
        searchView.maxWidth = Integer.MAX_VALUE
        searchMovie()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_restore_preferences -> restorePreferences()
            R.id.menu_filter -> {
                val intent = Intent(activity, PreferencesActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun restorePreferences() {
        sharedPreferences.edit()
            .putString(getString(R.string.preference_sort_by), "popularity.desc").apply()
        sharedPreferences.edit().putString(getString(R.string.preference_rating), "0").apply()
        sharedPreferences.edit().putString(getString(R.string.preference_year), "").apply()
        sharedPreferences.edit().putString(getString(R.string.preference_vote_count), "1000")
            .apply()

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
        val view = activity!!.currentFocus
        if (view != null) {
            val imm: InputMethodManager =
                activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    @SuppressLint("WrongConstant")
    private fun setUpRecyclerView(binding: FragmentMoviesListBinding) {
        val layoutManager = GridLayoutManager(
            binding.moviesGrid.context,
            num_columns,
            GridLayoutManager.VERTICAL,
            false
        )
        binding.moviesGrid.layoutManager = layoutManager
        binding.moviesGrid.setHasFixedSize(true)
    }
}
