package com.kadirdogan97.rickandmortyapp.data.local.datasource

import androidx.lifecycle.LiveData
import com.kadirdogan97.rickandmortyapp.data.local.dao.CharacterDao
import com.kadirdogan97.rickandmortyapp.data.model.Character

/**
 * Created by Kadir Doğan on 6/11/2020.
 */
class CharactersLocalDataSourceImpl(private val characterDao: CharacterDao): CharactersLocalDataSource {
    override fun addCharacters(characters: List<Character>){
        characterDao.addCharacters(characters = characters)
    }

    override fun addCharacter(character: Character){
        characterDao.addCharacter(character = character)
    }

    override fun getAllCharacters(): LiveData<List<Character>>{
        return characterDao.getAllCharacters()
    }


    override fun getCharacter(characterId: String): LiveData<Character>{
        return characterDao.getCharacterById(characterId = characterId)
    }

    override fun clear(){
        characterDao.clear()
    }

    override fun searchCharacters(searchString: String): LiveData<List<Character>> {
        return characterDao.searchCharacters(searchString)
    }
}