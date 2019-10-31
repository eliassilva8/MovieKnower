package com.ems.movieknower.favoriteMovies


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.ems.movieknower.MainActivity
import com.ems.movieknower.R
import com.ems.movieknower.database.FavoritesDatabase
import com.ems.movieknower.databinding.FragmentFavoriteMoviesBinding
import com.ems.movieknower.moviesList.MoviesListAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.main_activity.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class FavoriteMoviesFragment : Fragment() {
    val num_columns = 2
    lateinit var binding: FragmentFavoriteMoviesBinding
    lateinit var systemLanguage: String
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_favorite_movies, container, false
        )
        systemLanguage = Locale.getDefault().language

        val application = requireNotNull(this.activity).application
        val dataSource = FavoritesDatabase.getDatabase(application).favouritesDao
        val viewModelFactory = FavoriteMoviesViewModelFactory(dataSource, application)
        val favoritesViewModel =
            ViewModelProvider(this, viewModelFactory).get(FavoritesViewModel::class.java)

        binding.favoritesViewModel = favoritesViewModel
        binding.lifecycleOwner = this

        setUpAdapter(favoritesViewModel)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpRecyclerView(binding)

        mainActivity = activity as MainActivity
        mainActivity.supportActionBar!!.title = getString(R.string.favorites)

        mainActivity.findViewById<BottomNavigationView>(R.id.bottom_nav)
            .setupWithNavController(mainActivity.navController)
        mainActivity.bottom_nav.visibility = View.VISIBLE
    }

    @SuppressLint("WrongConstant")
    private fun setUpRecyclerView(binding: FragmentFavoriteMoviesBinding) {
        val layoutManager = GridLayoutManager(
            binding.moviesGrid.context,
            num_columns,
            GridLayoutManager.VERTICAL,
            false
        )
        binding.moviesGrid.layoutManager = layoutManager
        binding.moviesGrid.setHasFixedSize(true)
    }

    private fun setUpAdapter(favoritesViewModel: FavoritesViewModel) {
        val adapter = MoviesListAdapter(binding.moviesGrid.context)
        favoritesViewModel.allFavourites.observe(this, Observer { movies ->
            if (movies?.size != 0) {
                movies!!.let { adapter.setMovies(it) }
            } else {
                binding.emptyFavoritesTv.visibility = View.VISIBLE
                binding.moviesGrid.visibility = View.GONE
            }
        })
        binding.moviesGrid.adapter = adapter
    }
}
