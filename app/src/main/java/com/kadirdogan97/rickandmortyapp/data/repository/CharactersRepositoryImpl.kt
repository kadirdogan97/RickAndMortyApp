package com.kadirdogan97.rickandmortyapp.data.repository


import android.content.SharedPreferences
import com.kadirdogan97.rickandmortyapp.GetAllCharactersQuery
import com.kadirdogan97.rickandmortyapp.data.model.Character
import com.kadirdogan97.rickandmortyapp.data.model.Filter
import com.kadirdogan97.rickandmortyapp.data.remote.CharactersRemoteDataSource
import com.kadirdogan97.rickandmortyapp.common.Result
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Kadir Doğan on 6/11/2020.
 */

class CharactersRepositoryImpl(private val charactersRemoteDataSource: CharactersRemoteDataSource, private val sharedPreferences: SharedPreferences): CharactersRepository {
    override fun fetchCharacters(page: Int, searchQuery: String, filter: Filter): Observable<Result<GetAllCharactersQuery.Data>> {
        return return charactersRemoteDataSource
            .fetchCharacters(page, searchQuery, filter)
            .map<Result<GetAllCharactersQuery.Data>> {
                Result.Success(it)
            }.onErrorReturn { throwable ->
                Result.Error(throwable)
            }.subscribeOn(Schedulers.io())
    }

    override fun putString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value)?.apply()
    }

    override fun getString(key: String): String {
        return sharedPreferences.getString(key, "")!!
    }
}