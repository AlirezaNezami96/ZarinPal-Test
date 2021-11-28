package com.saviway.zarinpaltestproject.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.http.ApolloHttpCache
import com.apollographql.apollo.cache.http.DiskLruHttpCacheStore
import com.google.gson.Gson
import com.saviway.zarinpaltestproject.MyApp
import com.saviway.zarinpaltestproject.data.local.DatabaseService
import com.saviway.zarinpaltestproject.data.local.dao.ProfileDao
import com.saviway.zarinpaltestproject.data.local.dao.RepositoriesDao
import com.saviway.zarinpaltestproject.data.network.ProfileApi
import com.saviway.zarinpaltestproject.data.network.RepositoriesApi
import com.saviway.zarinpaltestproject.domain.repository.UserRepository
import com.saviway.zarinpaltestproject.domain.repository.UserRepositoryImpl
import com.saviway.zarinpaltestproject.util.AuthorizationInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import javax.inject.Singleton

/**
 * Created by Alireza Nezami on 11/11/2021.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): Context {
        return app as MyApp
    }

    @Singleton
    @Provides
    fun provideDatabaseService(context: Context): DatabaseService =
        Room.databaseBuilder(
            context,
            DatabaseService::class.java,
            "time_tracking_db"
        ).build()

    @Singleton
    @Provides
    fun provideProfileDao(databaseService: DatabaseService): ProfileDao =
        databaseService.profileDao()

    @Singleton
    @Provides
    fun provideRepositoriesDao(databaseService: DatabaseService): RepositoriesDao =
        databaseService.repositoriesDao()

    @Provides
    @Singleton
    fun provideGson(): Gson =
        Gson()

    @Singleton
    @Provides
    fun provideApolloClient(context: Context): ApolloClient {
        val file = File(context.cacheDir, "apolloCache")
        val size: Long = 1024 * 1024
        val cacheStore = DiskLruHttpCacheStore(file, size)

      return  ApolloClient.builder()
            .serverUrl("https://api.github.com/graphql")
            .httpCache(ApolloHttpCache(cacheStore))
            .okHttpClient(
                OkHttpClient.Builder()
                    .addInterceptor(AuthorizationInterceptor())
                    .addInterceptor(HttpLoggingInterceptor())
                    .build()
            )
            .build()
    }


    @Singleton
    @Provides
    fun provideProfileAPI(apolloClient: ApolloClient): ProfileApi =
        ProfileApi(apolloClient)

    @Singleton
    @Provides
    fun provideRepositoriesApi(apolloClient: ApolloClient): RepositoriesApi =
        RepositoriesApi(apolloClient)

//
//    @Singleton
//    @Provides
//    fun provideSharedPreferences(context: Context): SharedPreferenceUtil {
//        return SharedPreferenceUtil(
//            context.getSharedPreferences(
//                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE
//            )
//        )
//    }

    @Singleton
    @Provides
    fun provideUserRepository(
        profileDao: ProfileDao,
        repositoriesDao: RepositoriesDao,
        profileApi: ProfileApi,
        repositoriesApi: RepositoriesApi,
    ): UserRepository =
        UserRepositoryImpl(profileDao,repositoriesDao, profileApi, repositoriesApi) as UserRepository

}