package com.juanfe.project.weatherapp.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.search.SearchView
import com.juanfe.project.weatherapp.databinding.FragmentSearchBinding
import com.juanfe.project.weatherapp.ui.search.adapter.search.SearchHistoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var searchHistoryAdapter: SearchHistoryAdapter

    private val searchViewModel: SearchViewModel by viewModels()
    private var searchViewOpen = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        initObservers()
        initListeners()
        setUpRecyclerView()
        onBack()
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchViewModel.viewState.collect { viewState ->
                    updateUi(viewState)
                }
            }
        }
    }


    private fun initListeners() {
        binding.apply {

            searchView.addTransitionListener { _, transitionState, _ ->
                if (transitionState == SearchView.TransitionState.SHOWING) {
                    searchViewOpen = true
                    searchViewModel.handleIntent(UserIntent.TapSearch)
                }
                if (transitionState == SearchView.TransitionState.HIDING){
                    searchHistoryAdapter.updateList(listOf())
                }
            }

            searchView.editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    //Not necessary
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    //Not necessary

                }

                override fun afterTextChanged(query: Editable?) {
                    if (!query.isNullOrEmpty()) {
                        searchViewModel.handleIntent(UserIntent.SearchProduct(query.toString()))
                    }

                }
            })

            searchView.editText.setOnEditorActionListener { query, _, _ ->
                val text = query?.text.toString()
                searchBar.setText(text)
                searchView.hide()
                if (text.isNotEmpty())
                    searchViewModel.handleIntent(UserIntent.SearchProduct(text))
                true
            }
        }
    }


    private fun setUpRecyclerView() {

        // Rv - history products from the dataStore
        binding.searchResultsRv.layoutManager = LinearLayoutManager(requireContext())

        //listOf podria ser la ubicacion actual
        searchHistoryAdapter = SearchHistoryAdapter(listOf()) { query, search ->

        }
        binding.searchResultsRv.adapter = searchHistoryAdapter

    }

    private fun onBack() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (searchViewOpen) {
                        binding.searchView.hide()
                        searchViewOpen = false
                    } else {
                        isEnabled = false
                        requireActivity().onBackPressedDispatcher.onBackPressed()
                    }
                }
            })
    }

    private fun updateUi(viewState: SearchViewState) {
        when (viewState) {
            is SearchViewState.Error -> {}
            is SearchViewState.Loading -> {}
            is SearchViewState.Success -> {
                val searchLocation = viewState.searchLocationModel
                if (searchLocation.isNotEmpty()) {
                    searchHistoryAdapter.updateList(searchLocation)
                }
            }
        }

    }


}