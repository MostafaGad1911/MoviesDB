package mostafagad.projects.movies.data.repository

import mostafagad.projects.movies.data.datasource.MoviesApis
import mostafagad.projects.movies.data.datasource.model.MoviesDto
import mostafagad.projects.movies.domain.repository.MoviesRepo
import javax.inject.Inject

class MoviesRepositoryImp @Inject constructor(
    private val api:MoviesApis
):MoviesRepo{

    override suspend fun getAllMovies(): MoviesDto {
        return api.getAllMovies()

    }


}