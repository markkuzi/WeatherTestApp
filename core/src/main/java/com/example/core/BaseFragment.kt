package com.example.core

import android.text.Layout
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.core.view.children
import androidx.fragment.app.Fragment

abstract class BaseFragment(layoutId: Int) : Fragment(layoutId) {

    fun setViewStatusVisibility(
        root: ViewGroup, state: ViewState,
        onSuccess: () -> Unit,
        onError: () -> Unit,
        onLoading: () -> Unit
    ) {
        root.children.forEach { it.visibility = View.GONE }
        when (state) {
            is ViewState.Error -> onError()
            is ViewState.Loading -> onLoading()
            is ViewState.Success -> onSuccess()
        }
    }
}