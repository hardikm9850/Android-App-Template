package com.hardik.myapplication.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SongsEntity::class], version = 1)
abstract class SongsDatabase : RoomDatabase(){
    abstract fun profileDAO(): SongsDAO
}