package com.kadirdogan97.rickandmortyapp.ui.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kadirdogan97.rickandmortyapp.data.model.Character
import com.kadirdogan97.rickandmortyapp.databinding.CharacterItemBinding

/**
 * Created by Kadir Doğan on 6/12/2020.
 */
class CharacterListAdapter: RecyclerView.Adapter<CharacterListAdapter.CharacterListItemViewHolder>() {

    private var popularTvShows: MutableList<Character> = mutableListOf()
    private lateinit var itemClickListener: ItemClickListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterListItemViewHolder {
        val itemBinding = CharacterItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CharacterListItemViewHolder(itemBinding)
    }

    override fun getItemCount(): Int = popularTvShows.size

    override fun onBindViewHolder(holder: CharacterListItemViewHolder, position: Int) {
        holder.bind(getCharacter(position), itemClickListener)
    }

    private fun getCharacter(position: Int) = popularTvShows[position]
    fun clearCharacters() {
        popularTvShows.clear()
    }
    fun setCharacters(tvShows: List<Character>) {
        val beforeSize = popularTvShows.size
        popularTvShows.addAll(tvShows)
        notifyItemRangeInserted(beforeSize, tvShows.size)
    }
    fun setListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    inner class CharacterListItemViewHolder(private val binding: CharacterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(character: Character, itemClickListener: ItemClickListener) {
            with(binding) {
                textViewCharacterName.text = character.name
                textViewSpecies.text = character.species
                textViewGender.text = character.gender
                Glide.with(imageViewCharacterImage.context)
                    .load(character.image)
                    .into(imageViewCharacterImage)
                characterCardView.setOnClickListener {
                    itemClickListener.onClick(character)
                }
            }

        }

    }
}
interface ItemClickListener {
    fun onClick(character: Character)
}