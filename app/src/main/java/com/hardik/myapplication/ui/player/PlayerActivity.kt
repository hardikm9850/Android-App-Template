package com.hardik.myapplication.ui.player

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.hardik.myapplication.R
import com.hardik.myapplication.database.SongsEntity
import kotlinx.android.synthetic.main.activity_song_player.*

class PlayerActivity : AppCompatActivity() {
    private lateinit var simpleExoPlayer: SimpleExoPlayer
    private lateinit var mediaSource: MediaSource
    private lateinit var dataSourceFactory: DefaultDataSourceFactory

    private val viewModel: PlayerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_player)
        with(intent) {
            getParcelableExtra<SongsEntity>(DATA_TAG)?.let {
                prepareViews()
                viewModel.songEntity.value = it
            }
        }
    }

    private fun prepareViews() {
        with(viewModel) {
            songName.observe(this@PlayerActivity) {
                txtSongName.text = it
            }
            songArtist.observe(this@PlayerActivity) {
                txtSongArtist.text = it
            }
            songState.observe(this@PlayerActivity) {
                updateSongState(it)
            }
            songAlbumAvatar.observe(this@PlayerActivity) {
                Glide.with(applicationContext)
                    .load(it)
                    .into(imgSongAvatar)
            }
            audioLink.observe(this@PlayerActivity) {
                it?.let {
                    initialiseMusicPlayer(it)
                }
            }
        }
        btnPlay.setOnClickListener {
            viewModel.onButtonClicked()
        }
    }

    private fun updateSongState(songState: SongState) {
        var imageDrawable = R.drawable.ic_play_or_pause
        when (songState) {
            SongState.IDEAL -> {
                imageDrawable = R.drawable.ic_play_or_pause
            }
            SongState.PLAY -> {
                imageDrawable = R.drawable.ic_pause
                simpleExoPlayer.playWhenReady = true
            }
            SongState.PAUSE -> {
                imageDrawable = R.drawable.ic_play
                simpleExoPlayer.playWhenReady = false
            }
        }
        btnPlay.setImageResource(imageDrawable)
    }

    private fun initialiseMusicPlayer(songUri: String) {
        simpleExoPlayer = SimpleExoPlayer.Builder(applicationContext).build()
        dataSourceFactory = DefaultDataSourceFactory(
            this,
            Util.getUserAgent(applicationContext, getString(R.string.app_name))
        )
        mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(songUri))

        simpleExoPlayer.setMediaSource(mediaSource)
        simpleExoPlayer.prepare()
    }

    override fun onDestroy() {
        simpleExoPlayer.playWhenReady = false
        super.onDestroy()
    }

    companion object {
        const val DATA_TAG = "data"
    }
}