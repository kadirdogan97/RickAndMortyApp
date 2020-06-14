package com.kadirdogan97.rickandmortyapp.ui.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kadirdogan97.rickandmortyapp.KEY_FILTER_GENDER
import com.kadirdogan97.rickandmortyapp.KEY_FILTER_STATUS
import com.kadirdogan97.rickandmortyapp.KEY_SEARCH_QUERY
import com.kadirdogan97.rickandmortyapp.data.model.Character
import com.kadirdogan97.rickandmortyapp.data.model.Filter
import com.kadirdogan97.rickandmortyapp.common.ReactiveViewModel
import com.kadirdogan97.rickandmortyapp.common.Result
import com.kadirdogan97.rickandmortyapp.common.Status
import com.kadirdogan97.rickandmortyapp.common.doOnSuccess
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * Created by Kadir Doğan on 6/10/2020.
 */
class VMCharacterList(private val charactersUseCase: CharactersUseCase): ReactiveViewModel(){

    private val contents = MutableLiveData<List<Character>>()
    val contents_: LiveData<List<Character>> = contents

    private val status = MutableLiveData<CharactersStatusViewState>()
    val status_: LiveData<CharactersStatusViewState> = status


    private val hasChanging = MutableLiveData<Boolean>()
    val hasChanging_: LiveData<Boolean> = hasChanging

    fun fetchCharacters(page: Int) {
        charactersUseCase
            .fetchCharacters(page,charactersUseCase.getString(KEY_SEARCH_QUERY),Filter(charactersUseCase.getString(KEY_FILTER_STATUS),charactersUseCase.getString(KEY_FILTER_GENDER)))
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                onCharactersContentResultReady(it)
            }
            .subscribe({ resource ->
                onCharactersStatusResultReady(resource)
            }, {})
            .also { disposable.add(it)}
    }


    fun setFilters(status:String, gender:String){
        charactersUseCase.putString(KEY_FILTER_STATUS,status)
        charactersUseCase.putString(KEY_FILTER_GENDER,gender)
        hasChanging.value = true
    }
    fun setNonHasChange(){
        hasChanging.value = false
    }
    fun clearQueries(){
        charactersUseCase.putString(KEY_FILTER_STATUS,"")
        charactersUseCase.putString(KEY_FILTER_GENDER,"")
        charactersUseCase.putString(KEY_SEARCH_QUERY,"")
        hasChanging.value = false
    }

    fun setSearchQuery(query: String){
        charactersUseCase.putString(KEY_SEARCH_QUERY,query)
        hasChanging.value = true

    }

    private fun onCharactersStatusResultReady(resource: Result<List<Character>>) {

        val viewState = when (resource) {
            is Result.Success -> CharactersStatusViewState(
                Status.Content
            )
            is Result.Error -> CharactersStatusViewState(
                Status.Error(resource.exception)
            )
            Result.Loading -> CharactersStatusViewState(
                Status.Loading
            )
        }
        status.value = viewState
    }

    private fun onCharactersContentResultReady(results: List<Character>) {
        contents.value = results
    }

    fun getFilters() = Filter(charactersUseCase.getString(KEY_FILTER_STATUS),charactersUseCase.getString(KEY_FILTER_GENDER))


}