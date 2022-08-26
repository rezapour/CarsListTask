package me.rezapour.sixttask.utils

sealed class DataState<out R> {

    data class Success<T>(val data: T) : DataState<T>()
    data class Error(val message: String) : DataState<Nothing>()
    object Loading : DataState<Nothing>()
}