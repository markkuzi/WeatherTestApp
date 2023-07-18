package com.example.data.storage

interface Storage <T: Any> {

    fun save(source: T)

    fun load() : T

}