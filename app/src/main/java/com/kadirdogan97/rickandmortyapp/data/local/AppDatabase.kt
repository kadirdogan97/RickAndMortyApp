package com.kadirdogan97.rickandmortyapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kadirdogan97.rickandmortyapp.data.local.dao.CharacterDao

/**
 * Created by Kadir Doğan on 6/10/2020.
 */
@Database(
    entities = [Character::class],
    version = 1,
    exportSchema = false
) abstract class AppDatabase:RoomDatabase(){
    abstract fun getCharacterDao(): CharacterDao
}