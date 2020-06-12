package com.kadirdogan97.rickandmortyapp.data.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Kadir Doğan on 6/10/2020.
 */
@Entity(tableName = "characters")
data class Character(
    @PrimaryKey(autoGenerate = false) @NonNull
    @ColumnInfo(name = "character_id")
    var id: String,
    @ColumnInfo(name = "name")
    val name: String? = null,
    @ColumnInfo(name = "status")
    val status: String? = null,
    @ColumnInfo(name = "species")
    val species: String? = null,
    @ColumnInfo(name = "type")
    val type: String? = null,
    @ColumnInfo(name = "gender")
    val gender: String? = null,
    @ColumnInfo(name = "image")
    val image: String? = null
){
    constructor():this("","","", "","","","")
}