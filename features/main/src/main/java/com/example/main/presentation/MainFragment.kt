package com.example.main.presentation

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.core.BaseFragment
import com.example.core.onTryAgain
import com.example.core.setSimpleViewStatusVisibility
import com.example.main.R
import com.example.main.databinding.FragmentMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment(R.layout.fragment_main) {

    private val viewModel by viewModel<MainViewModel>()
    private val binding by viewBinding(FragmentMainBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.mainWeather.observe(viewLifecycleOwner) {
            binding.cityName.text = it.date
        }

        viewModel.viewState.observe(viewLifecycleOwner) {
            setSimpleViewStatusVisibility(root = binding.root, state = it)
        }

        onTryAgain(root = binding.root) {
            viewModel.loadWeather("санкт петербург")
        }

        binding.details.setOnClickListener {
            findNavController().navigate(Uri.parse("weatherTestApp://details"))
        }
        binding.forecast.setOnClickListener {
            findNavController().navigate(Uri.parse("weatherTestApp://forecast"))
        }
    }
}