package com.kadirdogan97.rickandmortyapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kadirdogan97.rickandmortyapp.data.model.Character
import com.kadirdogan97.rickandmortyapp.databinding.FragmentCharacterDetailBinding
import com.kadirdogan97.rickandmortyapp.databinding.FragmentCharacterListBinding

/**
 * Created by Kadir Doğan on 6/10/2020.
 */

class CharacterDetailFragment : Fragment() {

    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
        val ch = arguments?.getParcelable<Character>("character")
        return binding.root
    }

}