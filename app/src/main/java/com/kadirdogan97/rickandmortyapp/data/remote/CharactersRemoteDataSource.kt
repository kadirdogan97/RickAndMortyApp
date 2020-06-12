package com.kadirdogan97.rickandmortyapp.data.remote

import com.kadirdogan97.rickandmortyapp.GetAllCharactersQuery
import io.reactivex.Observable

/**
 * Created by Kadir Doğan on 6/11/2020.
 */
interface CharactersRemoteDataSource{

    fun fetchCharacters(page: Int): Observable<GetAllCharactersQuery.Data>

}