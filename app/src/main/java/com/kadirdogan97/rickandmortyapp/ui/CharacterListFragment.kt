package com.kadirdogan97.rickandmortyapp.ui

import android.content.ClipData
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.kadirdogan97.rickandmortyapp.R
import com.kadirdogan97.rickandmortyapp.SELECTION_ALL
import com.kadirdogan97.rickandmortyapp.data.CharactersStatusViewState
import com.kadirdogan97.rickandmortyapp.data.model.Character
import com.kadirdogan97.rickandmortyapp.databinding.FragmentCharacterListBinding
import com.kadirdogan97.rickandmortyapp.helper.EndlessScrollListener
import com.kadirdogan97.rickandmortyapp.helper.observeNonNull
import com.kadirdogan97.rickandmortyapp.helper.runIfNull
import com.kadirdogan97.rickandmortyapp.viewmodel.VMCharacterList
import kotlinx.android.synthetic.main.fragment_character_list.*
import okhttp3.internal.notifyAll
import org.koin.android.ext.android.bind
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Kadir Doğan on 6/10/2020.
 */
class CharacterListFragment : Fragment(){
    private val viewModel: VMCharacterList by viewModel()
    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!

    companion object{
        const val DEFAULT_PAGE = 1
    }
        private val charactersAdapter = CharacterListAdapter()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        observeAll()
        initListeners()
        savedInstanceState.runIfNull {
            fetchCharacters(DEFAULT_PAGE)
        }
        initCharactersRecyclerView()
        return binding.root
    }


    private fun observeAll(){
        viewModel.contents_.observeNonNull(this){
            renderCharacters(it)
        }
        viewModel.status_.observeNonNull(this){
            renderStatusResult(it)
        }
        viewModel.hasChanging_.observeNonNull(this){
            when(it){
                true -> {
                    fetchCharacters(DEFAULT_PAGE)
                    viewModel.setNonHasChange()
                }
            }
        }
    }

    private fun initListeners() {
        binding.filterButton.setOnClickListener {
            val navigationDialog = NavigationView()
            navigationDialog.setDialogResult(object: NavigationView.FilterDialogListener{
                override fun applyFilters(status: String, gender: String) {
                    clearCharacters()
                    viewModel.setFilters(if(status != SELECTION_ALL) status else "", if(gender != SELECTION_ALL) gender else "")
                }

            })
            navigationDialog.showFilterDialog(requireContext(), viewModel.getFilters())
        }
        binding.searchView.setOnQueryTextFocusChangeListener { v, hasFocus -> if(hasFocus) binding.filterButton.visibility = View.GONE else binding.filterButton.visibility = View.VISIBLE }
        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    clearCharacters()
                    viewModel.setSearchQuery(newText)
                }
                return true
            }

        })
        charactersAdapter.setListener(object: ItemClickListener{
            override fun onClick(character: Character) {
                val bundle = Bundle()
                bundle.putParcelable("character", character)
                findNavController().navigate(R.id.action_characterListFragment_to_characterDetailFragment, bundle)
            }

        })
    }
    fun fetchCharacters(page:Int){
        viewModel.fetchCharacters(page)
    }

    private fun initCharactersRecyclerView() {
        val gridLayoutManager = GridLayoutManager(context,2)
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

    private fun clearCharacters() {
        charactersAdapter.clearCharacters()
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