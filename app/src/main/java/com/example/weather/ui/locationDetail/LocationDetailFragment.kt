package com.example.weather.ui.locationDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.weather.databinding.LocationDetailFragmentBinding
import com.example.weather.extensions.liveDataObserve
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationDetailFragment : Fragment() {

    private lateinit var binding: LocationDetailFragmentBinding

    private val locationDetailViewModel by viewModels<LocationDetailViewModel>()

    private val args: LocationDetailFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = LocationDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        locationDetailViewModel.getLocationDetail(args.name)
    }

    private fun initObservers() {
        liveDataObserve(locationDetailViewModel.locationDetailUiModelState) { locationDetailUi(it ?: return@liveDataObserve) }
    }

    private fun locationDetailUi(locationDetailUiModelState: LocationDetailUiModelState) = locationDetailUiModelState.run {
        if (locationDetail != null) locationDetailSuccess(locationDetail)
    }

    private fun locationDetailSuccess(locationDetailUi: LocationDetailUi) {
        binding.textViewName.text = locationDetailUi.name
        binding.textViewCondition.text = locationDetailUi.forecastDays.toString()
    }
}
