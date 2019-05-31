package com.ems.movieknower

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import com.ems.movieknower.databinding.MoviesListActivityBinding
import android.app.SearchManager
import android.content.Context
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import com.ems.movieknower.data.ApiCall


class MoviesListActivity: AppCompatActivity() {
    val num_columns = 2
    val popular_filter = "popular"
    lateinit var searchView: SearchView
    lateinit var binding: MoviesListActivityBinding
    lateinit var apiCall: ApiCall

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.movies_list_activity)
        setSupportActionBar(binding.moviesListToolbar)

        setUpRecyclerView(binding)
        apiCall = ApiCall(binding)
        apiCall.moviesSortedBy(popular_filter)
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
            R.id.menu_refresh -> apiCall.moviesSortedBy(popular_filter)
        }
        return super.onOptionsItemSelected(item)
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