package mostafagad.projects.movies.ui.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import mostafagad.projects.movies.R
import mostafagad.projects.movies.data.local.MovieEntity
import mostafagad.projects.movies.data.local.MoviesDataSource
import mostafagad.projects.movies.databinding.ActivityMovieDetailsBinding
import mostafagad.projects.movies.domain.model.MovieModel
import java.io.Serializable
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetails : AppCompatActivity(), OnClickListener {

    @Inject
    lateinit var dataSource: MoviesDataSource

    var movies: ArrayList<MovieEntity> = ArrayList()

    private var movieFav: Boolean? = false

    private val movie: MovieModel by lazy {
        intent.serializable("movie")!!
    }

    private val moviesMovieDetailsBinding: ActivityMovieDetailsBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_movie_details)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        initData()
        bindDetails(item = movie)
        handleOnBack()
        Log.i("MOVIES", "$movies")

    }

    private fun initData() {
        movies.clear()
        movies.addAll(dataSource.getMovies())
        movieFav = if (movies.isEmpty()) false else movies.find { it.id == movie.id }?.fav
        if (movieFav == false || movieFav == null) {
            moviesMovieDetailsBinding.favMovieImg.setBackgroundResource(R.drawable.unfav)
        } else {
            moviesMovieDetailsBinding.favMovieImg.setBackgroundResource(R.drawable.ic_fav)
        }
    }

    private fun handleOnBack(){
        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                startActivity(Intent(this@MovieDetails , Movies::class.java))
            }
        })

    }

    private fun bindDetails(item: MovieModel) {
        moviesMovieDetailsBinding.movie = item
        moviesMovieDetailsBinding.favMovieImg.setOnClickListener(this)

    }

    private inline fun <reified T : Serializable> Intent.serializable(key: String): T? = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializableExtra(key,
            T::class.java)
        else -> @Suppress("DEPRECATION") getSerializableExtra(key) as? T
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.favMovieImg -> {

                val movieEntity = MovieEntity()
                movieEntity.id = movie.id
                movieEntity.fav = movieFav != true
                if (dataSource.movieFound(movieId = movie.id).isEmpty()) {
                    dataSource.addMovie(movieEntity = movieEntity)
                    moviesMovieDetailsBinding.favMovieImg.setBackgroundResource(R.drawable.ic_fav)

                }else{
                    dataSource.updateMovie(movieEntity = movieEntity)
                    initData()


                }


            }
        }

    }
}