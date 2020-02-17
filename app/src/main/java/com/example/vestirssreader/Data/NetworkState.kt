package com.example.vestirssreader.Data






enum class NetworkState(private val message:String) {
    LOADING("Загрузка"),
    SUCCESS("Успешно"),
    FAILURE("Неудача")
}