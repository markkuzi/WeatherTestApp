package com.example.core

sealed class ResponseResult(
    val message: String? = null,
) {
    class Success: ResponseResult()
    class Error(message: String?): ResponseResult(message = message)
}