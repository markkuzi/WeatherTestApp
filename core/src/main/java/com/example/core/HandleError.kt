package com.example.core

import retrofit2.HttpException
import java.net.UnknownHostException

interface HandleError<T : Any> {

    fun handle(e: Exception): T

    class Base(private val manageResources: ManageResources) : HandleError<String> {

        override fun handle(e: Exception) = when (e) {
            is HttpException -> {
                when (e.code()) {
                    BAD_REQUEST -> {
                        manageResources.string(R.string.bad_request_message)
                    }

                    UNAUTHORIZED -> {
                        manageResources.string(R.string.unauthorized_message)
                    }

                    NOT_FOUND -> {
                        manageResources.string(R.string.not_found_message)
                    }

                    TOO_MANY_REQUEST -> {
                        manageResources.string(R.string.too_many_request_message)
                    }

                    else -> {
                        manageResources.string(R.string.unexpected_error_message)
                    }
                }
            }

            is UnknownHostException -> manageResources.string(R.string.no_connection_message)
            else -> manageResources.string(R.string.unexpected_error_message)
        }

        companion object {
            private const val BAD_REQUEST = 400
            private const val UNAUTHORIZED = 401
            private const val NOT_FOUND = 404
            private const val TOO_MANY_REQUEST = 429
        }

    }
}
