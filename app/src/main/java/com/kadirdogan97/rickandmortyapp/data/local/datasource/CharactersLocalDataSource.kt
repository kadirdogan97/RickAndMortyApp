package com.kadirdogan97.rickandmortyapp.data.local.datasource

import androidx.lifecycle.LiveData
import com.kadirdogan97.rickandmortyapp.data.model.Character

/**
 * Created by Kadir Doğan on 6/11/2020.
 */
interface CharactersLocalDataSource {
    fun addCharacters(characters:List<Character>)
    fun addCharacter(character:Character)
    fun getAllCharacters(): LiveData<List<Character>>
    fun getCharacter(characterId:String): LiveData<Character>
    fun clear()
    fun searchCharacters(searchString:String): LiveData<List<Character>>
}