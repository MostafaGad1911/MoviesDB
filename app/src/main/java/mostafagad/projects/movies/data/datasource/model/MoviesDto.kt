package mostafagad.projects.movies.data.datasource.model

import mostafagad.projects.movies.domain.model.MovieModel

data class MoviesDto(
    val page:Int ,
    val results: ArrayList<MovieModel>,
)