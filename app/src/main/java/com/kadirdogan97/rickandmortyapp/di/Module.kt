package com.kadirdogan97.rickandmortyapp.di

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.apollographql.apollo.ApolloClient
import com.kadirdogan97.rickandmortyapp.AppConstants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

/**
 * Created by Kadir Doğan on 6/10/2020.
 */

val databaseModules = module {
    fun provideDatabase(application: Application): Database{
        return Room.databaseBuilder(
            application.applicationContext,
            Database::class.java,
            "rickmorty"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideCharacterDao(database: Database):CharacterDao = database.getCharacterDao()

    single { provideDatabase(application = androidApplication()) }
    single { provideCharacterDao(database = get()) }
}

val networkModules = module{
    fun provideOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    fun provideApolloClient(okHttpClient: OkHttpClient): ApolloClient {
        return ApolloClient.builder().okHttpClient(okHttpClient).serverUrl(AppConstants.BASE_GRAPHQL_URL).build()
    }

    single {provideOkHttpClient()}
    single { provideApolloClient(okHttpClient = get()) }
}

