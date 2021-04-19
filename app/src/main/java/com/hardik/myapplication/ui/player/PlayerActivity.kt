package com.hardik.myapplication.ui.player

import android.app.Activity
import android.os.Bundle
import com.hardik.myapplication.R
import com.hardik.myapplication.database.SongsEntity

class PlayerActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_player)
        val songsEntity = intent.getParcelableExtra<SongsEntity>(DATA_TAG)

    }

    companion object {
        const val DATA_TAG = "data"
    }
}