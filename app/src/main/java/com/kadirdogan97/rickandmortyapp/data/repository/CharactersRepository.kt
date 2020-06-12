package com.kadirdogan97.rickandmortyapp.data.repository

import com.kadirdogan97.rickandmortyapp.GetAllCharactersQuery
import com.kadirdogan97.rickandmortyapp.data.model.Character
import com.kadirdogan97.rickandmortyapp.helper.Result
import io.reactivex.Observable


/**
 * Created by Kadir Doğan on 6/11/2020.
 */
interface CharactersRepository{
    fun fetchCharacters(page: Int): Observable<Result<GetAllCharactersQuery.Data>>
    fun getCharacters(): List<Character>
}