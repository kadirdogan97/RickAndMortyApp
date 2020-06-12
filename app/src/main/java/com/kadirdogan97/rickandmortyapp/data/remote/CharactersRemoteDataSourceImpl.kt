package com.kadirdogan97.rickandmortyapp.data.remote

import android.annotation.SuppressLint
import android.util.Log
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.rx2.Rx2Apollo
import com.kadirdogan97.rickandmortyapp.GetAllCharactersQuery
import com.kadirdogan97.rickandmortyapp.data.model.Character
import com.kadirdogan97.rickandmortyapp.utils.Result
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Kadir Doğan on 6/11/2020.
 */
class CharactersRemoteDataSourceImpl(private val apolloClient: ApolloClient): CharactersRemoteDataSource{
    val characters = mutableListOf<Character>()

    @SuppressLint("CheckResult")
    override fun fetchCharacters(page: Int): Result<List<Character>> {
        return try {
            Log.d("PAGE:", page.toString())
            val observableResponse = Rx2Apollo.from(apolloClient.query(GetAllCharactersQuery(page, "morty","","","","")))
            observableResponse.observeOn(AndroidSchedulers.mainThread()).observeOn(Schedulers.io()).subscribe { response ->
                if (!response.hasErrors() && response.data != null) {
                    Log.d("DataSourceDATA:", response.data.toString())

                    val data = response.data
                    val next = data?.characters?.info?.next
                    val pages = data?.characters?.info?.pages
                    data?.characters?.results?.map {
                        val character = Character(
                            it?.id!!,
                            it.name,
                            it.status,
                            it.species,
                            it.type,
                            it.gender,
                            it
                                ?.image
                        )
                        characters.add(character)
//                        if (next != null && pages != null) {
//                            if (next <= pages) {
//                                fetchCharacters(next)
//                            }
//                        }
                    }
                }
            }
            Result.Success(characters)
        } catch (exception: Exception) {
            Result.Error(exception)
        }


    }

}