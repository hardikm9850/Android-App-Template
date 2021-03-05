package com.hardik.myapplication.base

sealed class Error {
    object Text : Error()
    object Toast : Error()
    object NoError : Error()
}