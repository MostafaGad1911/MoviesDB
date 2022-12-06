package mostafagad.projects.movies.ui.interfaces

import mostafagad.projects.movies.domain.model.MovieModel

interface MovieController {
    fun getDetails(movieModel: MovieModel)
}