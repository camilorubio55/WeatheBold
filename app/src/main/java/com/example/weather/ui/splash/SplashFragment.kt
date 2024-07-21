package com.example.weather.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.weather.databinding.SplashFragmentBinding
import com.example.weather.extensions.liveEventObserve
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private lateinit var binding: SplashFragmentBinding

    private val splashViewModel by viewModels<SplashViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = SplashFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        splashViewModel.navigateToSearchFragment()
    }

    private fun initObservers() {
        liveEventObserve(splashViewModel.navigateToSearchFragment) { navigateToSearchFragment() }
    }

    private fun navigateToSearchFragment() {
        val action = SplashFragmentDirections.actionSplashFragmentToSearchFragment()
        findNavController().navigate(action)
    }
}
