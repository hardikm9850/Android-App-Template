package com.hardik.myapplication.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hardik.myapplication.base.Error
import com.hardik.myapplication.base.UIController
import com.hardik.myapplication.database.SongsEntity
import com.hardik.myapplication.network.beans.Entry
import com.hardik.myapplication.usecase.FetchSongsFromDBUseCase
import com.hardik.myapplication.usecase.FetchTopSongsUseCase
import com.hardik.myapplication.usecase.InsertSongsUseCase
import com.hardik.myapplication.util.extension.doToggleLoadingStateOf
import com.hardik.myapplication.util.extension.mapToDB
import com.hardik.tinder.network.exception.NoConnectivityException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 *
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val songsUseCase: FetchTopSongsUseCase,
    private val fetchSongsFromDBUseCase: FetchSongsFromDBUseCase,
    private val insertSongsUseCase: InsertSongsUseCase
) : ViewModel(),
    CoroutineScope, UIController {

    private val limit = "20"
    private val TAG = MainViewModel::class.java.canonicalName

    val shouldShowNoInternetMessage = MutableLiveData<Error>()
    val shouldShowProgressbar = MutableLiveData<Boolean>()
    val songsData = MutableLiveData<List<SongsEntity>>()

    fun init() {
        fetchSongsFromDB()
    }

    private fun fetchSongsFromDB() {
        fetchSongsFromDBUseCase.build(Unit)
            .doToggleLoadingStateOf(this)
            .onEach {
                if (it.isEmpty()) {
                    fetchSongsFromApi()
                    return@onEach
                }
                songsData.postValue(it)
            }
            .catch {
                Log.e(TAG, "Error while fetching songs from DB")
            }
            .launchIn(this)
    }

    private fun fetchSongsFromApi() {
        songsUseCase.build(FetchTopSongsUseCase.Params(limit))
            .doToggleLoadingStateOf(this)
            .catch {
                if (it is NoConnectivityException) {
                    showNoInternetMessage()
                    return@catch
                }
                Log.e(TAG, "Error while fetching songs from API",it)
            }
            .onEach {
                storeSongsInDB(it.entryList)
            }
            .launchIn(this)
    }

    private fun storeSongsInDB(entryList: List<Entry>?) {
        entryList?.run {
            songsData.postValue(entryList.mapToDB())
            insertSongsUseCase.build(InsertSongsUseCase.Params(entryList))
                .doToggleLoadingStateOf(this@MainViewModel)
                .catch {
                    Log.e(TAG, "Error while storing songs in DB",it)
                }
                .launchIn(this@MainViewModel)
        }
    }

    private fun showNoInternetMessage() {
        val isDataEmpty = true
        if (isDataEmpty) {
            shouldShowNoInternetMessage.postValue(Error.Text)
        } else {
            shouldShowNoInternetMessage.postValue(Error.Toast)
        }
    }

    override val coroutineContext: CoroutineContext
        get() = viewModelScope.coroutineContext

    override fun setLoading(shouldLoad: Boolean) {
        shouldShowProgressbar.value = shouldLoad
    }
}