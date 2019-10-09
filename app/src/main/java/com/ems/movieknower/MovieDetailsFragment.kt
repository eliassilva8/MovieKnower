package com.ems.movieknower


import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.ems.movieknower.data.ApiCall
import com.ems.movieknower.data.FavouritesViewModel
import com.ems.movieknower.data.Movie
import com.ems.movieknower.data.MovieViewModel
import com.ems.movieknower.database.FavouritesRoomDatabase
import com.ems.movieknower.databinding.FragmentMovieDetailsBinding
import com.ems.movieknower.utils.loadPoster
import kotlinx.android.synthetic.main.main_activity.*

/**
 * A simple [Fragment] subclass.
 */
class MovieDetailsFragment : Fragment() {
    lateinit var binding: FragmentMovieDetailsBinding
    lateinit var apiCall: ApiCall
    var movie: Movie? = null
    private val image_backdrop_size = "w300/"
    private lateinit var mFavouritesViewModel: FavouritesViewModel
    var isFavorite = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_movie_details, container, false)

        val args: MovieDetailsFragmentArgs by navArgs()
        movie = args.StringMovieIntent

        mFavouritesViewModel = ViewModelProvider(this).get(FavouritesViewModel::class.java)
        mFavouritesViewModel.findMovie(movie!!.id)
        isFavorite = mFavouritesViewModel.currentMovie != null

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpMovieView()
        setUpRecyclerView(binding)
        loadPoster(binding.toolbarMoviePoster, movie?.backdrop, image_backdrop_size)

        val mainActivity = activity as MainActivity
        mainActivity.supportActionBar!!.title = movie!!.title
        mainActivity.bottom_nav.visibility = View.GONE

        val db =
            Room.databaseBuilder(
                activity!!.applicationContext,
                FavouritesRoomDatabase::class.java,
                "favourite_movies_database"
            ).build()

        //TODO Codigo para ir buscar todos os favoritos
        /*mFavouritesViewModel.allFavourites.observe(this, object : Observer<List<Movie>> {
            override fun onChanged(t: List<Movie>?) {

            }
        })*/
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_details, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        if (isFavorite) {
            menu.findItem(R.id.menu_favorite_on).isVisible = true
            menu.findItem(R.id.menu_favorite_off).isVisible = false
        } else {
            menu.findItem(R.id.menu_favorite_off).isVisible = true
            menu.findItem(R.id.menu_favorite_on).isVisible = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_favorite_off -> {
                isFavorite = true
                mFavouritesViewModel.insert(movie!!)
                Toast.makeText(
                    activity,
                    getString(R.string.added_to_favorites),
                    Toast.LENGTH_SHORT
                ).show()
                activity!!.invalidateOptionsMenu()
            }
            R.id.menu_favorite_on -> {
                isFavorite = false
                mFavouritesViewModel.delete(movie!!)
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
        apiCall.similarMovies(movie?.id)
    }

    private fun setUpMovieView() {
        //val args: MovieDetailsFragmentArgs by navArgs()
        //movie = args.StringMovieIntent
        binding.movieView = MovieViewModel()
        binding.movieView!!.movie = movie
    }
}
