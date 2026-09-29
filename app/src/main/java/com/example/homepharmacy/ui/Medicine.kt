package com.example.homepharmacy.ui

data class Medicine(
    val id: Int,
    val name: String,
    val form: String,          // "Таблетки" или "Сироп"
    val expirationDate: Long,  // timestamp в миллисекундах
    val quantity: Int
)