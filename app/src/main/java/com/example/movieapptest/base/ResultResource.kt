package com.example.movieapptest.base


sealed class ResultResource<out T>(val data:T?=null, val message:String?=null)
{
    class Success<T>(data: T?=null):ResultResource<T>(data)

    class ErrorMessage<T>(message: String?,data: T?=null):ResultResource<T>(data,message)

    class Loading<T>():ResultResource<T>()
}
