package com.kadirdogan97.rickandmortyapp.data

import com.kadirdogan97.rickandmortyapp.data.model.Character
import com.kadirdogan97.rickandmortyapp.data.repository.CharactersRepository
import com.kadirdogan97.rickandmortyapp.helper.Result
import com.kadirdogan97.rickandmortyapp.helper.map
import io.reactivex.Observable

/**
 * Created by Kadir Doğan on 6/11/2020.
 */
class CharactersUseCase(private val charactersRepository: CharactersRepository, private val mapper: CharactersMapper){

    fun fetchCharacters(page: Int): Observable<Result<List<Character>>> {
        return charactersRepository
            .fetchCharacters(page)
            .map { resource ->
                resource.map { response ->
                    mapper.mapFrom(response)
                }
            }.startWith(Result.Loading)
    }
}