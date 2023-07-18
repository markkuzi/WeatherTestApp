package com.example.core

interface Mapper<R: Any, S: Any> {

    fun map(source: S) : R

}