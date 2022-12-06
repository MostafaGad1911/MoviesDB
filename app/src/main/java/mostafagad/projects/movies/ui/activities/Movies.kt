package mostafagad.projects.movies.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import mostafagad.projects.movies.R
import mostafagad.projects.movies.databinding.MoviesActivityBinding
import mostafagad.projects.movies.domain.model.MovieModel
import mostafagad.projects.movies.ui.viewModels.MoviesVM
import mostafagad.projects.movies.ui.adapter.MoviesAdapter
import mostafagad.projects.movies.ui.interfaces.MovieController
import mostafagad.projects.movies.utils.Ext.hide
import mostafagad.projects.movies.utils.Ext.show
import mostafagad.projects.movies.utils.Ext.toast

@AndroidEntryPoint
class Movies : AppCompatActivity() , MovieController{

    private val mainActivityDataBinding:MoviesActivityBinding by lazy {
        DataBindingUtil.setContentView(this , R.layout.movies_activity)
    }

    private val viewModel: MoviesVM by viewModels()

    private val marvelCharactersList:ArrayList<MovieModel> = ArrayList()
    private val marvelCharactersAdapter: MoviesAdapter by lazy {
        MoviesAdapter(moviesList = marvelCharactersList , movieController = this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mainActivityDataBinding.root)
        prepareMarvelCharsRV()
        collectMarvelCharacters()
        collectMarvelCharacters()
    }

    private fun prepareMarvelCharsRV(){
        val lytManager = GridLayoutManager(
            this, 2,
            GridLayoutManager.VERTICAL, false
        )
        mainActivityDataBinding.marvelCharactersRV.layoutManager = lytManager
        mainActivityDataBinding.marvelCharactersRV.adapter = marvelCharactersAdapter

    }
    private fun collectMarvelCharacters(){
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.moviesDbValue.collect{
                when{
                    it.isLoading -> {
                       withContext(Dispatchers.Main){
                           mainActivityDataBinding.progressBar.show()
                       }
                    }
                    it.moviesList.isNotEmpty() -> {
                        withContext(Dispatchers.Main){
                            mainActivityDataBinding.progressBar.hide()
                            marvelCharactersList.clear()
                            marvelCharactersList.addAll(it.moviesList)
                            marvelCharactersAdapter.notifyItemRangeInserted(marvelCharactersList.size.plus(1) , it.moviesList.size)
                        }
                    }
                    it.error.isNotBlank() -> {
                        withContext(Dispatchers.Main){
                            mainActivityDataBinding.progressBar.hide()
                            it.error.toast(ctx = this@Movies)
                        }
                    }
                }
            }
        }

    }

    override fun getDetails(movieModel: MovieModel) {
        val detailsIntent = Intent(this , MovieDetails::class.java)
        detailsIntent.putExtra("movie" , movieModel)
        startActivity(detailsIntent)
    }
}