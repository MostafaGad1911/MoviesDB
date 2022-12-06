package mostafagad.projects.movies.data.local

class MoviesDataSource(private val moviesDao: MoviesDao) {
    fun getMovies() = moviesDao.getMovies()
    fun updateMovie(movieEntity:MovieEntity) = moviesDao.updateMovie(id  = movieEntity.id , fav = movieEntity.fav)
    fun movieFound(movieId:Long) = moviesDao.movieFound(id = movieId)
    fun addMovie(movieEntity:MovieEntity) = moviesDao.addMovie(movieEntity = movieEntity)

}