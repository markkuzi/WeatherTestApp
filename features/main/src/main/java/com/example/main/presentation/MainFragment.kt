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

class MainFragment : Fragment() {

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

        details.setOnClickListener {
            findNavController().navigate(Uri.parse("weatherTestApp://details"))
        }

        forecast.setOnClickListener {
            findNavController().navigate(Uri.parse("weatherTestApp://forecast"))
        }


    }


}