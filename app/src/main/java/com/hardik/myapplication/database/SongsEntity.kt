package com.hardik.myapplication.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class SongsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var name: String?,
    var title: String?,
    var imageUrl: String?,
    var audioLink: String?,
    var content: String?,
    var cover: String?,
    var artist: String?,
    val duration: Int?
) : Parcelable