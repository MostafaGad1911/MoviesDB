package mostafagad.projects.movies.domain.model

data class MovieModel(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Long,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    var fav:Boolean? = false,
    val video: Boolean,
    val vote_average: Int,
    val vote_count: Int
):java.io.Serializable