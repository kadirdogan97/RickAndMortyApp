package com.kadirdogan97.rickandmortyapp.di


import android.app.Application
import androidx.room.Room
import com.apollographql.apollo.ApolloClient
import com.kadirdogan97.rickandmortyapp.AppConstants
import com.kadirdogan97.rickandmortyapp.MyApplication
import com.kadirdogan97.rickandmortyapp.data.CharactersMapper
import com.kadirdogan97.rickandmortyapp.data.CharactersUseCase
import com.kadirdogan97.rickandmortyapp.data.local.AppDatabase
import com.kadirdogan97.rickandmortyapp.data.local.dao.CharacterDao
import com.kadirdogan97.rickandmortyapp.data.local.datasource.CharactersLocalDataSource
import com.kadirdogan97.rickandmortyapp.data.local.datasource.CharactersLocalDataSourceImpl
import com.kadirdogan97.rickandmortyapp.data.remote.CharactersRemoteDataSource
import com.kadirdogan97.rickandmortyapp.data.remote.CharactersRemoteDataSourceImpl
import com.kadirdogan97.rickandmortyapp.data.repository.CharactersRepository
import com.kadirdogan97.rickandmortyapp.data.repository.CharactersRepositoryImpl
import com.kadirdogan97.rickandmortyapp.viewmodel.VMMain
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

/**
 * Created by Kadir Doğan on 6/10/2020.
 */

val databaseModules = module {
    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            AppDatabase::class.java,
            "rickmorty"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideCharacterDao(database: AppDatabase): CharacterDao = database.getCharacterDao()

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

val dataSourceModules = module {
//    single<CharactersLocalDataSource>{ CharactersLocalDataSourceImpl(characterDao = get()) }
    single<CharactersRemoteDataSource> { CharactersRemoteDataSourceImpl(apolloClient = get()) }
}

val repositoryModules = module{
    single<CharactersRepository>{
        CharactersRepositoryImpl(charactersRemoteDataSource = get())
    }
}

val viewModelModules = module {
    viewModel {
        VMMain(fetchCharactersUseCase = get())
    }
}

val useCaseModules = module{
    single{
        CharactersUseCase(charactersRepository = get(), mapper = CharactersMapper())
    }
}



