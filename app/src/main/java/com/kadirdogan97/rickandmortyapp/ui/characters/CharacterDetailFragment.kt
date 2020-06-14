package com.kadirdogan97.rickandmortyapp.ui.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.kadirdogan97.rickandmortyapp.data.model.Character
import com.kadirdogan97.rickandmortyapp.databinding.FragmentCharacterDetailBinding

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
        val character = arguments?.getParcelable<Character>("character")
        setCharacter(character!!)
        return binding.root
    }
    private fun setCharacter(character: Character){
        binding.apply {
            textViewName.text = character.name
            textViewSpecies.text = character.species
            textViewStatus.text = character.status
            textViewGender.text = character.gender
            textViewType.text = character.type
            Glide.with(imageViewCharacter.context)
                .load(character.image)
                .into(imageViewCharacter)
        }

    }

}