package mostafagad.projects.movies.data.datasource

import mostafagad.projects.movies.data.datasource.model.MoviesDto
import mostafagad.projects.movies.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApis {

    @GET("discover/movie")
    suspend fun getAllMovies(
        @Query("api_key") apiKey:String = Constants.THE_MOVIE_DB_API_KEY ,
        @Query("primary_release_year") timeStamp:String = Constants.PRIMARY_RELEASE_YEAR,
        @Query("sort_by") sortBy:String = Constants.SORTED_BY,
    ):MoviesDto
}