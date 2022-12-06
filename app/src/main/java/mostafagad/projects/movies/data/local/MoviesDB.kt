package mostafagad.projects.movies.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MovieEntity::class] , version = 1)
abstract class MoviesDB : RoomDatabase() {

    abstract fun getMoviesDao():MoviesDao

    companion object{
        const val DB_NAME = "Movies-DataBase.db"
        @Volatile
        private var instance: MoviesDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }



        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            MoviesDB::class.java,
            DB_NAME
        ).fallbackToDestructiveMigration().build()
    }

}