package com.kadirdogan97.rickandmortyapp.di


import android.app.Application
import android.content.SharedPreferences
import androidx.room.Room
import com.apollographql.apollo.ApolloClient
import com.kadirdogan97.rickandmortyapp.BASE_GRAPHQL_URL
import com.kadirdogan97.rickandmortyapp.data.CharactersMapper
import com.kadirdogan97.rickandmortyapp.data.CharactersUseCase
import com.kadirdogan97.rickandmortyapp.data.remote.CharactersRemoteDataSource
import com.kadirdogan97.rickandmortyapp.data.remote.CharactersRemoteDataSourceImpl
import com.kadirdogan97.rickandmortyapp.data.repository.CharactersRepository
import com.kadirdogan97.rickandmortyapp.data.repository.CharactersRepositoryImpl
import com.kadirdogan97.rickandmortyapp.viewmodel.VMCharacterDetail
import com.kadirdogan97.rickandmortyapp.viewmodel.VMCharacterList
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



val networkModules = module{
    fun provideOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
                .build()
    }

    fun provideApolloClient(okHttpClient: OkHttpClient): ApolloClient {
        return ApolloClient.builder().okHttpClient(okHttpClient).serverUrl(BASE_GRAPHQL_URL).build()
    }

    single {provideOkHttpClient()}
    single { provideApolloClient(okHttpClient = get()) }
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
        VMMain(charactersUseCase = get())
    }
    viewModel {
        VMCharacterList(charactersUseCase = get())

    }
    viewModel {
        VMCharacterDetail(charactersUseCase = get())
    }
}

val useCaseModules = module{
    single{
        CharactersMapper()
    }
    single{
        CharactersUseCase(charactersRepository = get(), mapper = get())
    }
}
val appModule = module {

    single{ provideSharedPrefs(androidApplication()) }

    single<SharedPreferences.Editor> { provideSharedPrefs(androidApplication()).edit() }
}

fun provideSharedPrefs(androidApplication: Application): SharedPreferences{
    return  androidApplication.getSharedPreferences("default",  android.content.Context.MODE_PRIVATE)
}



