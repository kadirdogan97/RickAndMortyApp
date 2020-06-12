package com.kadirdogan97.rickandmortyapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kadirdogan97.rickandmortyapp.data.CharactersUseCase

/**
 * Created by Kadir Doğan on 6/10/2020.
 */
class VMCharacterList(private val charactersUseCase: CharactersUseCase): ViewModel(){

    val dataFetchState = MutableLiveData<Boolean>()
    val isFetching = MutableLiveData<Boolean>()


    fun getAllCharacters(){
        isFetching.postValue(true)
//        charactersUseCase.getCharacters()
        isFetching.postValue(false)
    }

//    fun searchCharaters(searchString:String)= liveData {
//        charactersUseCase.searchCharacters(searchString)
//    }


}