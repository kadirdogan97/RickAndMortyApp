package com.kadirdogan97.rickandmortyapp.helper

/**
 * Created by Kadir Doğan on 6/11/2020.
 */
interface Mapper<R, D> {
    fun mapFrom(type: R): D
}