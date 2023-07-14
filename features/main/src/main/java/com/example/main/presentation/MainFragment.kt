package com.example.main.presentation

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.main.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val details = view.findViewById<Button>(R.id.details)
        val forecast = view.findViewById<Button>(R.id.forecast)

        CoroutineScope(Dispatchers.Main).launch {
            delay(1000)
            viewModel.mainWeather.observe(viewLifecycleOwner) {
                details.text = it.name
            }

        }

        details.setOnClickListener {
            findNavController().navigate(Uri.parse("weatherTestApp://details"))
        }

        forecast.setOnClickListener {
            findNavController().navigate(Uri.parse("weatherTestApp://forecast"))
        }


    }


}