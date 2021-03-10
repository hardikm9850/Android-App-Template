package com.hardik.myapplication.network.beans

data class Customer(
    val customer: CustomerX,
    val imageUrl: String,
    val jobIndicator: String,
    val lastUpdateDate: String,
    val orderRef: String,
    val scheduleDate: String,
    val scheduleEndTime: String,
    val scheduleStartTime: String,
    val serviceRequested: String,
    val serviceSpecialInstructions: String,
    val status: String
)