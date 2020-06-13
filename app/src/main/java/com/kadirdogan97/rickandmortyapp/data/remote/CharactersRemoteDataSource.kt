package com.kadirdogan97.rickandmortyapp.data.remote

import com.kadirdogan97.rickandmortyapp.GetAllCharactersQuery
import com.kadirdogan97.rickandmortyapp.data.model.Filter
import io.reactivex.Observable

/**
 * Created by Kadir Doğan on 6/11/2020.
 */
interface CharactersRemoteDataSource{

    fun fetchCharacters(page: Int, searchQuery: String, filter: Filter): Observable<GetAllCharactersQuery.Data>

}