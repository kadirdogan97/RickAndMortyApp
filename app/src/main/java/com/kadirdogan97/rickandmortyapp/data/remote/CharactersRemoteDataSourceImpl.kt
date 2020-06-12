package com.kadirdogan97.rickandmortyapp.data.remote

import android.annotation.SuppressLint
import android.util.Log
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.rx2.rxQuery
import com.kadirdogan97.rickandmortyapp.GetAllCharactersQuery
import io.reactivex.Observable

/**
 * Created by Kadir Doğan on 6/11/2020.
 */
class CharactersRemoteDataSourceImpl(private val apolloClient: ApolloClient): CharactersRemoteDataSource{
//    val characters = mutableListOf<Character>()

    @SuppressLint("CheckResult")
    override fun fetchCharacters(page: Int): Observable<GetAllCharactersQuery.Data> {
            Log.d("PAGE:", page.toString())
           return  apolloClient.rxQuery(GetAllCharactersQuery(page, "morty","","","","")).map { it.data }
    }
}