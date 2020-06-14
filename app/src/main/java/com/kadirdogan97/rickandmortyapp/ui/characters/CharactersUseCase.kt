package com.kadirdogan97.rickandmortyapp.ui.characters

import com.kadirdogan97.rickandmortyapp.data.model.Character
import com.kadirdogan97.rickandmortyapp.data.model.Filter
import com.kadirdogan97.rickandmortyapp.data.repository.CharactersRepository
import com.kadirdogan97.rickandmortyapp.common.Result
import com.kadirdogan97.rickandmortyapp.common.map
import com.kadirdogan97.rickandmortyapp.ui.characters.CharactersMapper
import io.reactivex.Observable

/**
 * Created by Kadir Doğan on 6/11/2020.
 */
class CharactersUseCase(private val charactersRepository: CharactersRepository, private val mapper: CharactersMapper){

    fun fetchCharacters(page: Int, searchQuery: String, filter: Filter = Filter("","")): Observable<Result<List<Character>>> {
        return charactersRepository
            .fetchCharacters(page, searchQuery, filter)
            .map { resource ->
                resource.map { response ->
                    mapper.mapFrom(response)
                }
            }.startWith(Result.Loading)
    }

    fun putString(key: String, value: String) {
        charactersRepository.putString(key, value)
    }

    fun getString(key: String): String {
        return charactersRepository.getString(key)
    }
}