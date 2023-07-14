package com.example.core

enum class RequestCode(val code: Int?) {
    SUCCESS(code = 200),
    BAD_REQUEST(code = 400),
    UNAUTHORIZED(code = 401),
    NOT_FOUND(code = 404),
    TOO_MANY_REQUEST(code = 429),
    NO_INTERNET_ERROR(code = null),
    ERROR(code = null),
}

