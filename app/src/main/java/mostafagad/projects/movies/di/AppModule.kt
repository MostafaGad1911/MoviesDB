package mostafagad.projects.movies.di

import android.content.Context
import androidx.room.Room
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mostafagad.projects.movies.BuildConfig
import mostafagad.projects.movies.MoviesApps
import mostafagad.projects.movies.data.datasource.MoviesApis
import mostafagad.projects.movies.data.local.MoviesDB
import mostafagad.projects.movies.data.local.MoviesDataSource
import mostafagad.projects.movies.data.repository.MoviesRepositoryImp
import mostafagad.projects.movies.domain.repository.MoviesRepo
import mostafagad.projects.movies.utils.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideContext():Context = MoviesApps.context()

    @Provides
    fun provideChucker(context: Context): ChuckerInterceptor {
        val chuckerCollector = ChuckerCollector(
            context = context,
            // Toggles visibility of the notification
            showNotification = true,
            // Allows to customize the retention period of collected data
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )
        return ChuckerInterceptor.Builder(context)
            .collector(chuckerCollector)
            .maxContentLength(250_000L)
            .redactHeaders("Auth-Token", "Bearer")
            .alwaysReadResponseBody(true)
            .build()
    }
    @Provides
    fun provideLogger(): Interceptor = if (BuildConfig.DEBUG) HttpLoggingInterceptor().setLevel(
        HttpLoggingInterceptor.Level.BODY) else HttpLoggingInterceptor()

    @Provides
    fun provideHttpClient(chuckerInterceptor: ChuckerInterceptor ,logging: Interceptor): OkHttpClient =  OkHttpClient.Builder()
        .addInterceptor(logging)
        .callTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .followRedirects(false)
        .addInterceptor(chuckerInterceptor)
        .followSslRedirects(false)
        .writeTimeout(50, TimeUnit.SECONDS)
        .addInterceptor { chain ->
            val request: Request =
                chain.request()
                    .newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .build()
            chain.proceed(request)
        }.build()

    @Provides
    @Singleton
    fun provideMarvelApis(okHttpClient: OkHttpClient):MoviesApis = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(MoviesApis::class.java)

    @Provides
    @Singleton
    fun provideMarvelRepo(apis: MoviesApis):MoviesRepo = MoviesRepositoryImp(api = apis)

    @Provides
    @Singleton
    fun provideRoom(context: Context): MoviesDB = Room.databaseBuilder(
        context.applicationContext,
        MoviesDB::class.java,
        MoviesDB.DB_NAME
    ).fallbackToDestructiveMigration()
        .allowMainThreadQueries().build()


    @Provides
    @Singleton
    fun provideDataSource(moviesDB: MoviesDB): MoviesDataSource =
        MoviesDataSource(moviesDao = moviesDB.getMoviesDao())


}