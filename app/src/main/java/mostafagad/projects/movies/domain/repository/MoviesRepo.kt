package mostafagad.projects.movies.domain.repository

import mostafagad.projects.movies.data.datasource.model.MoviesDto

interface MoviesRepo {
    suspend fun getAllMovies():MoviesDto
}