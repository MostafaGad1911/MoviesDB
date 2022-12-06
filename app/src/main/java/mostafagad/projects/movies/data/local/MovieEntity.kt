package mostafagad.projects.movies.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "movies")
class MovieEntity{
    @ColumnInfo
    @PrimaryKey
    var id:Long = 0

    @ColumnInfo
    var fav: Boolean = false

}