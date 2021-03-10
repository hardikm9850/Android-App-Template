package com.hardik.myapplication.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface OrderDAO {
    @Query("SELECT * from OrderEntity")
    fun getFavoriteProfiles() : List<OrderEntity>

    @Insert
    fun insertProfile(order : OrderEntity)
}