package com.kadirdogan97.rickandmortyapp.ui.characters

import com.kadirdogan97.rickandmortyapp.GetAllCharactersQuery
import com.kadirdogan97.rickandmortyapp.data.model.Character
import com.kadirdogan97.rickandmortyapp.common.Mapper

/**
 * Created by Kadir Doğan on 6/12/2020.
 */
class CharactersMapper: Mapper<GetAllCharactersQuery.Data, List<Character>> {
    val characters = mutableListOf<Character>()
    override fun mapFrom(type: GetAllCharactersQuery.Data): List<Character> {
        return type.characters?.results?.map { itemResponse ->
            Character(
                itemResponse?.id!!,
                itemResponse.name,
                itemResponse.status,
                itemResponse.species,
                itemResponse.type,
                itemResponse.gender,
                itemResponse.image
            )
        }!!
    }
}