package com.kadirdogan97.rickandmortyapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kadirdogan97.rickandmortyapp.data.model.Character

/**
 * Created by Kadir Doğan on 6/10/2020.
 */
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCharacter(character: Character)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCharacters(characters:List<Character>)

    @Query("DELETE FROM characters")
    fun clear()

    @Query("SELECT * FROM characters")
    fun getAllCharacters(): LiveData<List<Character>>

    @Query("SELECT * FROM characters WHERE character_id=:characterId ")
    fun getCharacterById(characterId:String): LiveData<Character>

    @Query("SELECT * FROM characters WHERE name LIKE :searchString OR type LIKE :searchString OR status LIKE :searchString OR gender LIKE :searchString OR species LIKE :searchString")
    fun searchCharacters(searchString: String) : LiveData<List<Character>>
}