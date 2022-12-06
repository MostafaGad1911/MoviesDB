package mostafagad.projects.movies.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MoviesDao {

    @Query("SELECT * FROM movies")
    fun getMovies():List<MovieEntity>

    @Insert
    fun addMovie(movieEntity: MovieEntity)

    @Query("SELECT * FROM movies WHERE id = :id")
    fun movieFound(id:Long):List<MovieEntity>

    @Query("UPDATE movies SET fav =:fav WHERE id = :id")
    fun updateMovie(id:Long , fav:Boolean): Int

}