package com.ems.movieknower


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

/**
 * A simple [Fragment] subclass.
 */
class MovieDetailsFragment : Fragment() {
    lateinit var binding: FragmentMovieDetailsBinding
    lateinit var apiCall: ApiCall
    var movie: Movie? = null
    private val image_backdrop_size = "w300/"
    private lateinit var mFavouritesViewModel: FavouritesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_movie_details, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpMovieView()
        setUpRecyclerView(binding)
        loadPoster(binding.toolbarMoviePoster, movie?.backdrop, image_backdrop_size)
        mFavouritesViewModel = ViewModelProvider(this).get(FavouritesViewModel::class.java)

        val db =
            Room.databaseBuilder(
                activity!!.applicationContext,
                FavouritesRoomDatabase::class.java,
                "favourite_movies_database"
            )
                .build()

        //TODO Codigo para ir buscar todos os favoritos
        /*mFavouritesViewModel.allFavourites.observe(this, object : Observer<List<Movie>> {
            override fun onChanged(t: List<Movie>?) {

            }
        })*/

        binding.favouriteBtn.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                mFavouritesViewModel.insert(movie!!)
                Toast.makeText(
                    activity,
                    getString(R.string.added_to_favourites),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                mFavouritesViewModel.delete(movie!!)
                Toast.makeText(
                    activity,
                    getString(R.string.deleted_from_favourites),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
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
        val args: MovieDetailsFragmentArgs by navArgs()
        movie = args.StringMovieIntent

        //movie = fragment.arguments!!.getParcelable(getString(R.string.movie_intent))

        //val bundle = activity!!.intent.extras?.getBundle(getString(R.string.movie_intent))
        //movie = bundle?.getParcelable(getString(R.string.movie_intent))
        binding.movieView = MovieViewModel()
        binding.movieView!!.movie = movie
    }
}
