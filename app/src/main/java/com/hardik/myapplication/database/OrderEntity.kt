package com.hardik.myapplication.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class OrderEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val orderId : String,
    val orderStatus : Int = 0,
    val scheduledDate : String,
    val firstName : String,
    val lastName : String,
    val address : String,
    val city : String,
    val phone : String,
    val serviceType : String,
    val instruction : String,
    val imageUrl : String
)