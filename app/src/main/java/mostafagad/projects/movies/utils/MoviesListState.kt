package mostafagad.projects.movies.utils

import mostafagad.projects.movies.domain.model.MovieModel

data class MoviesListState(
    val isLoading:Boolean = false ,
    val  moviesList:List<MovieModel> = emptyList() ,
    val error:String = ""
)