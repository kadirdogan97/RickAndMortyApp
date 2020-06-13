package com.kadirdogan97.rickandmortyapp.data.remote

import android.annotation.SuppressLint
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.rx2.rxQuery
import com.kadirdogan97.rickandmortyapp.GetAllCharactersQuery
import com.kadirdogan97.rickandmortyapp.data.model.Filter
import io.reactivex.Observable

/**
 * Created by Kadir Doğan on 6/11/2020.
 */
class CharactersRemoteDataSourceImpl(private val apolloClient: ApolloClient): CharactersRemoteDataSource{


    @SuppressLint("CheckResult")
    override fun fetchCharacters(page: Int, searchQuery: String, filter: Filter): Observable<GetAllCharactersQuery.Data> {
        val query = GetAllCharactersQuery(
            page,
            searchQuery,
            filter.status?:"",
            filter.gender?:""
        )
        return  apolloClient.rxQuery(query).map { it.data }
    }
}