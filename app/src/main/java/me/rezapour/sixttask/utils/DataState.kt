package me.rezapour.sixttask.utils

sealed class DataState<out R> {

    data class Success<T>(val data: T) : DataState<T>()
    data class Error(val messageId: Int) : DataState<Nothing>()
    object DefaultError : DataState<Nothing>()
    object Loading : DataState<Nothing>()
}