package com.hardik.myapplication.network.beans

data class OrderResponse(
    val customers: List<Customer>,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String
)