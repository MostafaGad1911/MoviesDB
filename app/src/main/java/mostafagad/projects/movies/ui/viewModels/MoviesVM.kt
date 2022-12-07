package mostafagad.projects.movies.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import mostafagad.projects.movies.domain.model.MovieModel
import mostafagad.projects.movies.domain.repository.MoviesRepo
import mostafagad.projects.movies.utils.MoviesListState
import mostafagad.projects.movies.utils.Response
import javax.inject.Inject

@HiltViewModel
class MoviesVM @Inject constructor(private val marvelRepo: MoviesRepo) : ViewModel() {

    private val moviesDbState = MutableStateFlow(MoviesListState())
    var moviesDbValue: StateFlow<MoviesListState> = moviesDbState


    init {
        getAllMovies()
    }

    private fun getAllMovies() = viewModelScope.launch(Dispatchers.IO) {
        invoke().collect {
            when (it) {
                is Response.Success -> {
                    moviesDbState.value = MoviesListState(moviesList = it.data ?: emptyList())
                }
                is Response.Loading -> {
                    moviesDbState.value = MoviesListState(isLoading = true)
                }
                is Response.Error -> {
                    moviesDbState.value =
                        MoviesListState(error = it.message ?: "An Unexpected Error")
                }
            }
        }

    }

    fun invoke(): Flow<Response<ArrayList<MovieModel>>> = flow {
        try {
            emit(Response.Loading())
            val list = marvelRepo.getAllMovies()
            emit(Response.Success(list.results))
        } catch (e: Exception) {
            emit(Response.Error(e.printStackTrace().toString()))

        }
    }.distinctUntilChanged()

}