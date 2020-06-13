package com.kadirdogan97.rickandmortyapp.common

interface Mapper<R, D> {
    fun mapFrom(type: R): D
}