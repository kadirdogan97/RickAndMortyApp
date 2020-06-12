package com.kadirdogan97.rickandmortyapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kadirdogan97.rickandmortyapp.R
import com.kadirdogan97.rickandmortyapp.viewmodel.VMCharacterList
import com.kadirdogan97.rickandmortyapp.viewmodel.VMMain
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Kadir Doğan on 6/10/2020.
 */
class CharacterListFragment : Fragment() {
    private val viewModel: VMCharacterList by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_character_list, container, false)
    }

}