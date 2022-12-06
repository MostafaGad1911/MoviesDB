package mostafagad.projects.movies.utils

import java.math.BigInteger
import java.security.MessageDigest

object Constants {
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val timeStamp = "1"
    const val PRIMARY_RELEASE_YEAR = "2010"
    const val SORTED_BY = "vote_average.desc"
    const val API_KEY = "8f3f0c9f7620fa3ef4af31536c74a72d"
    const val PRIVATE_KEY = "d1fb030b6962099c20e546af7f4298854b9581cd"

    const val THE_MOVIE_DB_API_KEY = "b026d74fb3791b3a28ec0af1fe542e58"
    const val API_READ_ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiMDI2ZDc0ZmIzNzkxYjNhMjhlYzBhZjFmZTU0MmU1OCIsInN1YiI6IjYxNDFhMmMwOTQ1MGZlMDA0MzEwNzQ4MyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.v_qr4_yL8FcsaqfcTfe-RNgZUzCWMMtjahrkGst2FCs"

    fun hash():String  {
        val input = "$timeStamp$PRIVATE_KEY$API_KEY"
        val md = MessageDigest.getInstance("MD5")
        return  BigInteger(1 ,md.digest(input.toByteArray())).toString(16).padStart(32 , '0')
    }
}