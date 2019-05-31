package com.ems.movieknower

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import com.ems.movieknower.databinding.MoviesListActivityBinding
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast


class MoviesListActivity: AppCompatActivity() {
    val num_columns = 2
    lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: MoviesListActivityBinding = DataBindingUtil.setContentView(this, R.layout.movies_list_activity)
        setSupportActionBar(binding.moviesListToolbar)

        setUpRecyclerView(binding)
        getDataFromWebService(binding)

        /*if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                Toast.makeText(this, "working", Toast.LENGTH_LONG).show()
            }
        }*/
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        // Inflate menu to add items to action bar if it is present.
        inflater.inflate(R.menu.menu_main, menu)
        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.menu_search).getActionView() as SearchView
        searchView.setSearchableInfo(
            searchManager.getSearchableInfo(componentName)
        )
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(this@MoviesListActivity, "working", Toast.LENGTH_LONG).show()
                closeKeyboard()
                return true
            }
        })

        return true
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