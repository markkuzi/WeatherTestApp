package com.example.splash.presentation

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.splash.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashScreenFragment : Fragment() {

    private val coroutine by lazy { CoroutineScope(Dispatchers.Main) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       coroutine.launch {
            delay(SPLASH_SCREEN_TIME)
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.splashScreenFragment, true)
                .build()
            findNavController().navigate(Uri.parse("weatherTestApp://main"), navOptions)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutine.cancel()
    }

    companion object {
        private const val SPLASH_SCREEN_TIME = 1000L
    }
}