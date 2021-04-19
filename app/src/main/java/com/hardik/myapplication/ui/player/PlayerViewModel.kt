package com.hardik.myapplication.ui.player

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hardik.myapplication.database.SongsEntity

class PlayerViewModel : ViewModel() {
    val songName = MutableLiveData<String>()
    val songArtist = MutableLiveData<String>()
    val songState = MutableLiveData<SongState>()

    private lateinit var currentState : SongState

    fun init(songsEntity: SongsEntity) {
        currentState = SongState.IDEAL

        songName.postValue(songsEntity.name)
        songArtist.postValue(songsEntity.artist)
        songState.postValue(currentState)
    }

    fun onButtonClicked() {
        when(currentState){
            SongState.IDEAL, SongState.PAUSE -> playSong()
            SongState.PLAY -> pauseSong()
        }
    }

    private fun pauseSong() {

    }

    private fun playSong() {

    }
}