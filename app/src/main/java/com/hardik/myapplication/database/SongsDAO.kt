package com.hardik.myapplication.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SongsDAO {
    @Query("SELECT * from SongsEntity")
    fun getSongs() : List<SongsEntity>

    @Insert
    fun insertSongs(songs : List<SongsEntity>)
}