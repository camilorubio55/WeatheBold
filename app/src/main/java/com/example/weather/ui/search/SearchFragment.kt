package com.example.weather.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.weather.databinding.SearchFragmentBinding
import com.example.weather.extensions.liveDataObserve
import com.example.weather.extensions.liveEventObserve
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: SearchFragmentBinding

    private val searchViewModel by viewModels<SearchViewModel>()

    private val locationsAdapter by lazy { LocationAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = SearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initRecyclerView()
        initAdapter()
        searchViewModel.getLocations("Text")
    }

    private fun initObservers() {
        liveDataObserve(searchViewModel.locationUiModelState) { locationUi(it ?: return@liveDataObserve) }
        liveEventObserve(searchViewModel.navigateToLocationDetail) { navigateToLocationDetail(it) }
    }

    private fun navigateToLocationDetail(name: String) {
        val action = SearchFragmentDirections.actionSearchFragmentToLocationDetailFragment(name)
        findNavController().navigate(action)
    }

    private fun locationUi(locationUiModelState: LocationUiModelState) = locationUiModelState.run {
        if (locations.isNotEmpty()) locationSuccess(locations)
    }

    private fun locationSuccess(locations: List<LocationUi>) {
        locationsAdapter.set(locations)
    }

    private fun initRecyclerView() = binding.locationRecycler.run {
        adapter = locationsAdapter
    }

    private fun initAdapter() {
        locationsAdapter.onLocationListener = {
            searchViewModel.navigateToLocationDetail(it)
        }
    }
}
