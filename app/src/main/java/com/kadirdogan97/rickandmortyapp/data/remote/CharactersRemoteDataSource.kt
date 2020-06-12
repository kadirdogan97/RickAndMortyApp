package com.kadirdogan97.rickandmortyapp.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import com.apollographql.apollo.ApolloClient
import com.kadirdogan97.rickandmortyapp.GetAllCharactersQuery
import com.kadirdogan97.rickandmortyapp.data.model.Character
import com.kadirdogan97.rickandmortyapp.utils.Result
import java.lang.Exception

/**
 * Created by Kadir Doğan on 6/11/2020.
 */
interface CharactersRemoteDataSource{

    fun fetchCharacters(page: Int): Result<List<Character>>

}