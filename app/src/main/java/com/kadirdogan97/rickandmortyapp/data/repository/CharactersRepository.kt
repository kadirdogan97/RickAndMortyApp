package com.kadirdogan97.rickandmortyapp.data.repository

import com.kadirdogan97.rickandmortyapp.GetAllCharactersQuery
import com.kadirdogan97.rickandmortyapp.data.model.Character
import com.kadirdogan97.rickandmortyapp.data.model.Filter
import com.kadirdogan97.rickandmortyapp.common.Result
import io.reactivex.Observable


/**
 * Created by Kadir Doğan on 6/11/2020.
 */
interface CharactersRepository{
    fun fetchCharacters(page: Int, searchQuery: String, filter: Filter): Observable<Result<GetAllCharactersQuery.Data>>
    fun putString(key: String, value: String)
    fun getString(key: String): String
}