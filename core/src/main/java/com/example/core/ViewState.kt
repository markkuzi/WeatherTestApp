package com.example.core

sealed class ViewState(
    val message: String? = null
)  {
    class Success: ViewState()
    class Loading: ViewState()
    class Error(message: String?): ViewState(message = message)
}

