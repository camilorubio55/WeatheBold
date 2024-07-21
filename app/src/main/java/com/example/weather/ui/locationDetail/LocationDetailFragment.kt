package com.example.weather.ui.locationDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.weather.R
import com.example.weather.databinding.LocationDetailFragmentBinding
import com.example.weather.exceptions.ApiRequestException
import com.example.weather.extensions.hideOrShow
import com.example.weather.extensions.liveDataObserve
import com.example.weather.extensions.showError
import com.example.weather.extensions.snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationDetailFragment : Fragment() {

    private lateinit var binding: LocationDetailFragmentBinding

    private val locationDetailViewModel by viewModels<LocationDetailViewModel>()

    private val weatherAdapter by lazy { WeatherAdapter() }

    private val args: LocationDetailFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = LocationDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initRecyclerView()
        locationDetailViewModel.getLocationDetail(args.name)
    }

    private fun initRecyclerView() = binding.forecasterRecycler.run {
        adapter = weatherAdapter
    }

    private fun initObservers() {
        liveDataObserve(locationDetailViewModel.locationDetailUiModelState) { locationDetailUi(it ?: return@liveDataObserve) }
    }

    private fun locationDetailUi(locationDetailUiModelState: LocationDetailUiModelState) = locationDetailUiModelState.run {
        binding.detailProgress.hideOrShow(showProgress)
        if (locationDetail != null) locationDetailSuccess(locationDetail)
        if (exception != null) locationDetailError(exception)
    }

    private fun locationDetailSuccess(locationDetailUi: LocationDetailUi) = locationDetailUi.run {
        binding.container.hideOrShow(visible = true)
        binding.textViewLocationName.text = name
        binding.textViewCondition.text = currentDetail.condition.conditionText
        binding.textViewTemperature.text = currentDetail.temperature
        Glide.with(requireContext())
                .load(currentDetail.condition.conditionIcon)
                .circleCrop()
                .into(binding.imageViewWeather)
        weatherAdapter.set(forecastDays)
    }

    private fun locationDetailError(exception: Exception) {
        binding.container.hideOrShow(visible = false)
        showErrorSnackBar(exception)
    }

    private fun showErrorSnackBar(exception: Exception) = exception.run {
        when (this) {
            is ApiRequestException -> snackbar(messageError).showError()
            else -> snackbar(R.string.error_unknown).showError()
        }
    }
}
