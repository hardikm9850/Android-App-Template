package com.hardik.myapplication.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hardik.myapplication.base.Error
import com.hardik.myapplication.base.UIController
import com.hardik.myapplication.database.OrderEntity
import com.hardik.myapplication.usecase.FetchOrderUseCase
import com.hardik.myapplication.util.extension.doToggleLoadingStateOf
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
    private val fetchOrderUseCase: FetchOrderUseCase
) :
    ViewModel(),
    CoroutineScope, UIController {

    val shouldShowNoInternetMessage = MutableLiveData<Error>()
    val ordersLiveData = MutableLiveData<MutableList<OrderEntity>>()
    val shouldShowProgressbar = MutableLiveData<Boolean>()

    fun init() {
        fetchOrderUseCase.build(Unit)
            .doToggleLoadingStateOf(this)
            .onEach {
                mapData(it)
                shouldShowNoInternetMessage.postValue(Error.NoError)
            }
            .catch {
                if (it is NoConnectivityException) {
                    showNoInternetMessage()
                }
            }
            .launchIn(this)
    }

    private fun mapData(orderEntityList: MutableList<OrderEntity>) {
        ordersLiveData.postValue(orderEntityList)
    }

    private fun showNoInternetMessage() {
        val isDataEmpty = ordersLiveData.value?.isNullOrEmpty() ?: true
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