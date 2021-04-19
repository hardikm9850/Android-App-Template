package com.hardik.myapplication.ui.player

sealed class SongState {
    object IDEAL : SongState()
    object PLAY : SongState()
    object PAUSE : SongState()
}
