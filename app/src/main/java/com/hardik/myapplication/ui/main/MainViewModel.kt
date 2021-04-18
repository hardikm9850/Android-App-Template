package com.hardik.myapplication.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hardik.myapplication.base.Error
import com.hardik.myapplication.base.UIController
import com.hardik.myapplication.database.SongsEntity
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

) : ViewModel(),
    CoroutineScope, UIController {

    val shouldShowNoInternetMessage = MutableLiveData<Error>()
    val shouldShowProgressbar = MutableLiveData<Boolean>()


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