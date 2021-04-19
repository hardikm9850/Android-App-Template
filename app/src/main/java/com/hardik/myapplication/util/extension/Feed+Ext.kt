package com.hardik.myapplication.util.extension

import com.hardik.myapplication.database.SongsEntity
import com.hardik.myapplication.network.beans.Entry

fun List<Entry>.mapToDB(): List<SongsEntity> {
    val songsList = mutableListOf<SongsEntity>()
    for (entity in this) {
        songsList.add(
            SongsEntity(
                name = entity.name,
                title = entity.title,
                imageUrl = entity.imageUrl,
                audioLink = entity.link.audioLink,
                content = entity.content,
                cover = entity.cover,
                artist = entity.artist,
                duration = entity.duration
            )
        )
    }
    return songsList
}

