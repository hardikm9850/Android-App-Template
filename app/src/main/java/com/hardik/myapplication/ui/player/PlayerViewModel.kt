package com.hardik.myapplication.ui.player

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.hardik.myapplication.database.SongsEntity
import com.hardik.myapplication.ui.player.SongState.*

class PlayerViewModel : ViewModel() {
    val songEntity = MutableLiveData<SongsEntity>()
    val songName = Transformations.map(songEntity, SongsEntity::name)
    val songArtist = Transformations.map(songEntity, SongsEntity::artist)
    val audioLink = Transformations.map(songEntity, SongsEntity::audioLink)
    val songAlbumAvatar = Transformations.map(songEntity, SongsEntity::imageUrl)

    var songState = MutableLiveData<SongState>(IDEAL)

    fun onButtonClicked() {
        when (songState.value) {
            IDEAL, PAUSE -> playSong()
            PLAY -> pauseSong()
        }
    }

    private fun pauseSong() {
        songState.postValue(PAUSE)
    }

    private fun playSong() {
        songState.postValue(PLAY)
    }
}