package com.kadirdogan97.rickandmortyapp.data.repository


import com.kadirdogan97.rickandmortyapp.GetAllCharactersQuery
import com.kadirdogan97.rickandmortyapp.data.model.Character
import com.kadirdogan97.rickandmortyapp.data.remote.CharactersRemoteDataSource
import com.kadirdogan97.rickandmortyapp.helper.Result
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Kadir Doğan on 6/11/2020.
 */

class CharactersRepositoryImpl(private val charactersRemoteDataSource: CharactersRemoteDataSource): CharactersRepository {
    override fun fetchCharacters(page: Int): Observable<Result<GetAllCharactersQuery.Data>> {
        return return charactersRemoteDataSource
            .fetchCharacters(page)
            .map<Result<GetAllCharactersQuery.Data>> {
                Result.Success(it)
            }.onErrorReturn { throwable ->
                Result.Error(throwable)
            }.subscribeOn(Schedulers.io())
    }
    override fun getCharacters(): List<Character> {
        return emptyList()
    }
}