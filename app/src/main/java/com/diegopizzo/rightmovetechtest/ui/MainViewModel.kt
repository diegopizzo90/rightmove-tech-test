package com.diegopizzo.rightmovetechtest.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.diegopizzo.network.base.Result
import com.diegopizzo.network.interactor.IPropertiesInteractor
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(
    private val interactor: IPropertiesInteractor,
    private val disposables: CompositeDisposable,
    private val subscriberScheduler: Scheduler = Schedulers.io(),
    private val observerScheduler: Scheduler = AndroidSchedulers.mainThread()
) : ViewModel() {

    private val _viewStates: MutableLiveData<MainViewState> = MutableLiveData()
    val viewStates: LiveData<MainViewState> = _viewStates

    private var _viewState: MainViewState? = null
    var viewState: MainViewState
        get() = _viewState
            ?: throw UninitializedPropertyAccessException("\"viewState\" was queried before being initialized")
        set(value) {
            _viewState = value
            _viewStates.value = value
        }

    init {
        viewState = MainViewState()
    }

    fun getAveragePropertiesPrice() {
        disposables.add(
            interactor.getAveragePropertyPrice()
                .subscribeOn(subscriberScheduler)
                .observeOn(observerScheduler)
                .doOnSubscribe { onLoading() }
                .subscribe({ result ->
                    when (result) {
                        is Result.Success -> onSuccess(result.data.average)
                        is Result.Error -> onError()
                    }
                }, {
                    onError()
                })
        )
    }

    private fun onLoading() {
        viewState = viewState.copy(isLoading = true, averagePrice = "")
    }

    private fun onSuccess(averagePrice: String) {
        viewState = viewState.copy(isLoading = false, averagePrice = averagePrice)
    }

    private fun onError() {
        viewState = viewState.copy(isLoading = false, averagePrice = null)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}

data class MainViewState(
    val isLoading: Boolean? = null,
    val averagePrice: String? = ""
)