package com.hardik.myapplication.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.hardik.myapplication.R
import com.hardik.myapplication.base.Error
import com.hardik.myapplication.base.Error.*
import com.hardik.myapplication.database.SongsEntity
import com.hardik.myapplication.ui.main.adapter.SongAdapter
import com.hardik.myapplication.ui.player.PlayerActivity
import com.hardik.myapplication.ui.player.PlayerActivity.Companion.DATA_TAG
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter : SongAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        bindViewModel()
    }

    private fun bindViewModel() {
        with(viewModel) {
            shouldShowNoInternetMessage.observe(this@MainActivity) {
                mapError(it)
            }
            shouldShowProgressbar.observe(this@MainActivity) {
                if (it) {
                    rootNoInternet.isVisible = false
                    rcvSongList.isVisible = false
                }
                rootProgress.isVisible = it
            }
            songsData.observe(this@MainActivity){
                rcvSongList.isVisible = true
                setupRecyclerView(it)
            }
            init()
        }
    }

    private fun setupRecyclerView(list: List<SongsEntity>) {
        adapter = SongAdapter(list){
            onItemClicked(it)
        }
        rcvSongList.adapter = adapter
        rcvSongList.layoutManager = LinearLayoutManager(applicationContext)
    }

    private fun onItemClicked(songsEntity: SongsEntity) {
        with(Intent(this, PlayerActivity::class.java)) {
            this.putExtra(DATA_TAG, songsEntity)
            startActivity(this)
        }
    }

    private fun mapError(error: Error?) {
        when (error) {
            NoError -> {
                rootNoInternet.isVisible = false
                rcvSongList.isVisible = true
            }
            Toast -> {
                makeText(
                    applicationContext,
                    R.string.no_internet_toast_message,
                    LENGTH_LONG
                ).show()
            }
            Text -> {
                rootNoInternet.isVisible = true
                rcvSongList.isVisible = false
            }
        }
    }
}