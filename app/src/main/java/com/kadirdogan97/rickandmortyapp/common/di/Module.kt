package com.kadirdogan97.rickandmortyapp.common.di


import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.http.ApolloHttpCache
import com.apollographql.apollo.cache.http.DiskLruHttpCacheStore
import com.kadirdogan97.rickandmortyapp.BASE_GRAPHQL_URL
import com.kadirdogan97.rickandmortyapp.ui.characters.CharactersMapper
import com.kadirdogan97.rickandmortyapp.ui.characters.CharactersUseCase
import com.kadirdogan97.rickandmortyapp.data.remote.CharactersRemoteDataSource
import com.kadirdogan97.rickandmortyapp.data.remote.CharactersRemoteDataSourceImpl
import com.kadirdogan97.rickandmortyapp.data.repository.CharactersRepository
import com.kadirdogan97.rickandmortyapp.data.repository.CharactersRepositoryImpl
import com.kadirdogan97.rickandmortyapp.ui.characters.VMCharacterList
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by Kadir Doğan on 6/10/2020.
 */



val networkModules = module{
    fun provideOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
                .build()
    }

    fun provideApolloClient(okHttpClient: OkHttpClient, context: Context): ApolloClient {
        val file = File(context.cacheDir, "apolloCache")
        val size: Long = 1024 * 1024
        val cacheStore = DiskLruHttpCacheStore(file, size)

        return ApolloClient.builder().okHttpClient(okHttpClient).httpCache(ApolloHttpCache(cacheStore)).serverUrl(BASE_GRAPHQL_URL).build()
    }

    single {provideOkHttpClient()}
    single { provideApolloClient(context = androidContext(), okHttpClient = get()) }
}

val dataSourceModules = module {
    single<CharactersRemoteDataSource> { CharactersRemoteDataSourceImpl(apolloClient = get()) }
}

val repositoryModules = module{
    single<CharactersRepository>{
        CharactersRepositoryImpl(charactersRemoteDataSource = get(), sharedPreferences = get())
    }
}

val viewModelModules = module {
    viewModel {
        VMCharacterList(
            charactersUseCase = get()
        )
    }
}

val useCaseModules = module{
    single{
        CharactersMapper()
    }
    single{
        CharactersUseCase(
            charactersRepository = get(),
            mapper = get()
        )
    }
}
val appModule = module {

    single{ provideSharedPrefs(androidApplication()) }

    single<SharedPreferences.Editor> { provideSharedPrefs(androidApplication()).edit() }
}

fun provideSharedPrefs(androidApplication: Application): SharedPreferences{
    return  androidApplication.getSharedPreferences("default",  android.content.Context.MODE_PRIVATE)
}



