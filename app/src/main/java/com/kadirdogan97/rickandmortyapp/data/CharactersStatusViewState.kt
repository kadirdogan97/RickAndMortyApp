package com.kadirdogan97.rickandmortyapp.data

import com.kadirdogan97.rickandmortyapp.helper.Status

/**
 * Created by Kadir Doğan on 6/12/2020.
 */
class CharactersStatusViewState(
    val status: Status
) {
    fun isLoading() = status is Status.Loading

    fun getErrorMessage() = if (status is Status.Error) status.exception.message else ""

    fun shouldShowErrorMessage() = status is Status.Error
}