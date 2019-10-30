package com.ems.movieknower.movieDetails


import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.ems.movieknower.MainActivity
import com.ems.movieknower.R
import com.ems.movieknower.data.ApiCall
import com.ems.movieknower.data.Movie
import com.ems.movieknower.data.MoviesListViewModel
import com.ems.movieknower.database.FavoritesDatabase
import com.ems.movieknower.databinding.FragmentMovieDetailsBinding
import com.ems.movieknower.favoriteMovies.FavoriteMoviesViewModelFactory
import com.ems.movieknower.favoriteMovies.FavoritesViewModel
import com.ems.movieknower.utils.loadPoster
import kotlinx.android.synthetic.main.main_activity.*

/**
 * A simple [Fragment] subclass.
 */
class MovieDetailsFragment : Fragment() {
    lateinit var binding: FragmentMovieDetailsBinding
    lateinit var apiCall: ApiCall
    lateinit var movie: Movie
    private val image_backdrop_size = "w300/"
    var isFavorite = false
    lateinit var mFavoritesViewModel: FavoritesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_movie_details, container, false
            )

        val args: MovieDetailsFragmentArgs by navArgs()
        movie = args.StringMovieIntent

        val application = requireNotNull(this.activity).application
        val dataSource = FavoritesDatabase.getDatabase(application).favouritesDao
        val viewModelFactory = FavoriteMoviesViewModelFactory(dataSource, application)
        mFavoritesViewModel =
            ViewModelProvider(this, viewModelFactory).get(FavoritesViewModel::class.java)

        binding.favoritesViewModel = mFavoritesViewModel
        binding.lifecycleOwner = this

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpMovieView()
        setUpRecyclerView(binding)
        loadPoster(binding.toolbarMoviePoster, movie.backdrop, image_backdrop_size)

        val mainActivity = activity as MainActivity
        mainActivity.supportActionBar!!.title = movie.title
        mainActivity.bottom_nav.visibility = View.GONE

        val db =
            Room.databaseBuilder(
                activity!!.applicationContext,
                FavoritesDatabase::class.java,
                "favourite_movies_database"
            ).build()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_details, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        var isFavorite: Boolean
        mFavoritesViewModel.allFavourites.observe(this, Observer { movies ->
            isFavorite = movies?.find { it.id.equals(movie.id) } != null
            menu.findItem(R.id.menu_favorite_on).isVisible = isFavorite
            menu.findItem(R.id.menu_favorite_off).isVisible = !isFavorite
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_favorite_off -> {
                isFavorite = true
                mFavoritesViewModel.insert(movie)
                Toast.makeText(
                    activity,
                    getString(R.string.added_to_favorites),
                    Toast.LENGTH_SHORT
                ).show()
                activity!!.invalidateOptionsMenu()
            }
            R.id.menu_favorite_on -> {
                isFavorite = false
                mFavoritesViewModel.delete(movie)
                Toast.makeText(
                    activity,
                    getString(R.string.deleted_from_favorites),
                    Toast.LENGTH_SHORT
                ).show()
                activity!!.invalidateOptionsMenu()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUpRecyclerView(binding: FragmentMovieDetailsBinding) {
        val layoutManager = LinearLayoutManager(
            binding.similarMoviesRv.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.similarMoviesRv.layoutManager = layoutManager

        apiCall = ApiCall(binding)
        apiCall.similarMovies(movie.id)
    }

    private fun setUpMovieView() {
        binding.movieView = MoviesListViewModel()
        binding.movieView!!.movie = movie
    }
}
