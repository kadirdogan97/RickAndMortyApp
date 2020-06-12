package com.kadirdogan97.rickandmortyapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kadirdogan97.rickandmortyapp.data.CharactersStatusViewState
import com.kadirdogan97.rickandmortyapp.data.CharactersUseCase
import com.kadirdogan97.rickandmortyapp.data.model.Character
import com.kadirdogan97.rickandmortyapp.helper.ReactiveViewModel
import com.kadirdogan97.rickandmortyapp.helper.Result
import com.kadirdogan97.rickandmortyapp.helper.Status
import com.kadirdogan97.rickandmortyapp.helper.doOnSuccess
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * Created by Kadir Doğan on 6/10/2020.
 */
class VMCharacterList(private val charactersUseCase: CharactersUseCase): ReactiveViewModel(){

    private val contents = MutableLiveData<List<Character>>()
    val contents_: LiveData<List<Character>> = contents

    private val status = MutableLiveData<CharactersStatusViewState>()
    val status_: LiveData<CharactersStatusViewState> = status


    fun fetchCharacters(page: Int) {
        charactersUseCase
            .fetchCharacters(page)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                onCharactersContentResultReady(it)
            }
            .subscribe({ resource ->
                onCharactersStatusResultReady(resource)
            }, {})
            .also { disposable.add(it)}
    }



    private fun onCharactersStatusResultReady(resource: Result<List<Character>>) {

        val viewState = when (resource) {
            is Result.Success -> CharactersStatusViewState(Status.Content)
            is Result.Error -> CharactersStatusViewState(Status.Error(resource.exception))
            Result.Loading -> CharactersStatusViewState(Status.Loading)
        }
        status.value = viewState
    }

    private fun onCharactersContentResultReady(results: List<Character>) {
        contents.value = results
    }


}