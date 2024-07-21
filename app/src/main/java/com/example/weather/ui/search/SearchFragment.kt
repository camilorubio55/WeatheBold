package com.example.weather.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.weather.R
import com.example.weather.databinding.SearchFragmentBinding
import com.example.weather.exceptions.ApiRequestException
import com.example.weather.exceptions.ApiRequestException.ConnectionNetwork
import com.example.weather.extensions.getSearchView
import com.example.weather.extensions.hide
import com.example.weather.extensions.hideOrShow
import com.example.weather.extensions.ifNotEmpty
import com.example.weather.extensions.liveDataObserve
import com.example.weather.extensions.liveEventObserve
import com.example.weather.extensions.onQueryTextListener
import com.example.weather.extensions.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: SearchFragmentBinding

    private val searchViewModel by viewModels<SearchViewModel>()

    private val locationsAdapter by lazy { LocationAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        initMenuProvider()
        binding = SearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initRecyclerView()
        initAdapter()
    }

    private fun initMenuProvider() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.search_menu, menu)
                val searchView = menu.getSearchView(R.id.search_item)
                searchView?.onQueryTextListener(onQueryTextChange = { query -> query.ifNotEmpty { searchViewModel.getLocations(query) } })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }
        }, viewLifecycleOwner)
    }

    private fun initRecyclerView() = binding.locationRecycler.run {
        adapter = locationsAdapter
    }

    private fun initAdapter() {
        locationsAdapter.onLocationListener = {
            searchViewModel.navigateToLocationDetail(it)
        }
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
        binding.searchProgress.hideOrShow(showProgress)
        locationSuccess(locations)
        if (exception != null) locationError(exception)
    }

    private fun locationSuccess(locations: List<LocationUi>) {
        locationsAdapter.set(locations)
        if (locations.isEmpty()) showNoResults() else showResults()
    }

    private fun showNoResults() {
        binding.locationRecycler.hide()
        binding.messageView.showNotFoundError()
    }

    private fun showResults() {
        binding.locationRecycler.visible()
        binding.messageView.hide()
    }

    private fun locationError(exception: Exception) =exception.run {
        binding.locationRecycler.hide()
        binding.messageView.visible()
        when (this) {
            is ConnectionNetwork -> binding.messageView.showConnectionError()
            is ApiRequestException -> binding.messageView.showGenericError()
            else -> binding.messageView.showGenericError()
        }
    }
}
