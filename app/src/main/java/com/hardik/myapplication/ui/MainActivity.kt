package com.hardik.myapplication.ui

import android.os.Bundle
import android.widget.Toast.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.hardik.myapplication.R
import com.hardik.myapplication.base.Error
import com.hardik.myapplication.base.Error.*
import com.hardik.myapplication.database.OrderEntity
import com.hardik.myapplication.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()


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
            ordersLiveData.observe(this@MainActivity) {
                setupRecyclerView(it)
                rcvOrderList.isVisible = true
            }
            shouldShowProgressbar.observe(this@MainActivity) {
                if (it) {
                    rootNoInternet.isVisible = false
                    rcvOrderList.isVisible = false
                }
                rootProgress.isVisible = it
            }
        }
    }

    private fun setupRecyclerView(list: MutableList<OrderEntity>?) {

    }

    private fun mapError(error: Error?) {
        when (error) {
            NoError -> {
                rootNoInternet.isVisible = false
                rcvOrderList.isVisible = true
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
                rcvOrderList.isVisible = false
            }
        }
    }
}