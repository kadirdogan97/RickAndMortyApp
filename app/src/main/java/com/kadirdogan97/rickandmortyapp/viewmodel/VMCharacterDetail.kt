package com.kadirdogan97.rickandmortyapp.viewmodel

import androidx.lifecycle.ViewModel
import com.kadirdogan97.rickandmortyapp.data.CharactersUseCase
import com.kadirdogan97.rickandmortyapp.helper.ReactiveViewModel

/**
 * Created by Kadir Doğan on 6/10/2020.
 */

class VMCharacterDetail(private val charactersUseCase: CharactersUseCase): ReactiveViewModel(){}
