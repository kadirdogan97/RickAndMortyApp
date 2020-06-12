package com.kadirdogan97.rickandmortyapp.ui

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.kadirdogan97.rickandmortyapp.R
import com.kadirdogan97.rickandmortyapp.data.CharactersStatusViewState
import com.kadirdogan97.rickandmortyapp.data.model.Character
import com.kadirdogan97.rickandmortyapp.databinding.FragmentCharacterListBinding
import com.kadirdogan97.rickandmortyapp.helper.EndlessScrollListener
import com.kadirdogan97.rickandmortyapp.helper.observeNonNull
import com.kadirdogan97.rickandmortyapp.helper.runIfNull
import com.kadirdogan97.rickandmortyapp.viewmodel.VMCharacterList
import com.kadirdogan97.rickandmortyapp.viewmodel.VMMain
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Kadir Doğan on 6/10/2020.
 */
class CharacterListFragment : Fragment() {
    private val viewModel: VMCharacterList by viewModel()
    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!

    private val charactersAdapter = CharacterListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCharacterListBinding.inflate(inflater, container, false)

        viewModel.contents_.observeNonNull(this){
            renderCharacters(it)
        }
        viewModel.status_.observeNonNull(this){
            renderStatusResult(it)
        }
        savedInstanceState.runIfNull {
            fetchCharacters(MainActivity.DEFAULT_PAGE)
        }
        initCharactersRecyclerView()
        return binding.root
    }

    fun fetchCharacters(page:Int){
        viewModel.fetchCharacters(page)
    }

    private fun initCharactersRecyclerView() {
        val gridLayoutManager = GridLayoutManager(context, 2)
        binding.recyclerView.apply {
            adapter = charactersAdapter
            layoutManager = gridLayoutManager
            addOnScrollListener(object : EndlessScrollListener(gridLayoutManager) {
                override fun onLoadMore(page: Int) {
                    fetchCharacters(page)
                }
            })
        }
    }

    private fun renderCharacters(contents: List<Character>) {
        charactersAdapter.setCharacters(contents)
    }

    private fun renderStatusResult(statusViewState: CharactersStatusViewState) {
        binding.progressHorizontal.visibility = if (statusViewState.isLoading()) View.VISIBLE else View.GONE
        binding.viewStateMessage.visibility = if (statusViewState.shouldShowErrorMessage()) View.VISIBLE else View.GONE
        binding.viewStateMessage.text = statusViewState.getErrorMessage()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}