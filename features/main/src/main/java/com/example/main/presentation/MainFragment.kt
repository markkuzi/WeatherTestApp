package com.example.main.presentation

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
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
    private var searchCity: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.mainWeather.observe(viewLifecycleOwner) {
            binding.cityName.text = it.name
            binding.tvTemp.text = it.temp
            binding.tvUpdateTime.text = it.date
            binding.tvDescription.text = it.description
            binding.tvHumidity.text = it.humidity
            binding.tvWindSpeed.text = it.windSpeed
            binding.tvPressure.text = it.pressure
            binding.ivIcon.setImageResource(it.icon)
        }

        viewModel.viewState.observe(viewLifecycleOwner) {
            setSimpleViewStatusVisibility(root = binding.root, state = it)
        }

        onTryAgain(root = binding.root) {
            showSearchDialog()
        }

        binding.btnSearch.setOnClickListener {
            showSearchDialog()
        }

        binding.btnRefresh.setOnClickListener {
            viewModel.loadWeather(binding.cityName.text.toString())
        }

        binding.btnDetails.setOnClickListener {
            findNavController().navigate(Uri.parse("weatherTestApp://details"))
        }
        binding.btnForecast.setOnClickListener {
            findNavController().navigate(Uri.parse("weatherTestApp://forecast"))
        }
    }

    private fun showSearchDialog() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(layoutInflater.inflate(R.layout.search_dialog, null))
        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_background)
        val etCity: EditText = dialog.findViewById(R.id.etCity)
        etCity.setText(searchCity)
        val btnSearchWeather: Button = dialog.findViewById(R.id.btnSearchWeather)
        dialog.show()

        btnSearchWeather.setOnClickListener {
            searchCity = etCity.text.toString()
            viewModel.loadWeather(searchCity.toString())
            dialog.hide()
        }
    }
}