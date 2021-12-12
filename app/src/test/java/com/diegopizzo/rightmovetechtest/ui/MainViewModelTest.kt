package com.diegopizzo.rightmovetechtest.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.diegopizzo.network.base.Result
import com.diegopizzo.network.interactor.IPropertiesInteractor
import com.diegopizzo.network.model.AveragePrice
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel

    @Mock
    private lateinit var interactor: IPropertiesInteractor

    @Mock
    private lateinit var stateObserver: Observer<MainViewState>

    @Before
    fun setUp() {
        viewModel = MainViewModel(
            interactor, CompositeDisposable(), Schedulers.trampoline(), Schedulers.trampoline()
        ).apply {
            viewStates.observeForever(stateObserver)
        }
    }

    @Test
    fun getAveragePropertiesPrice_successResult_verifyLoadingState() {
        `when`(interactor.getAveragePropertyPrice()).thenReturn(Single.just(successResult))

        viewModel.getAveragePropertiesPrice()
        verify(stateObserver).onChanged(
            MainViewState(isLoading = true)
        )
    }

    @Test
    fun getAveragePropertiesPrice_successResult_verifyAverageState() {
        `when`(interactor.getAveragePropertyPrice()).thenReturn(Single.just(successResult))

        viewModel.getAveragePropertiesPrice()
        verify(stateObserver).onChanged(
            MainViewState(isLoading = false, average)
        )
    }

    @Test
    fun getAveragePropertiesPrice_errorResult_verifyLoadingState() {
        `when`(interactor.getAveragePropertyPrice()).thenReturn(Single.error(Exception("any error")))

        viewModel.getAveragePropertiesPrice()
        verify(stateObserver).onChanged(
            MainViewState(isLoading = true)
        )
    }

    @Test
    fun getAveragePropertiesPrice_errorResult_verifyAverageState() {
        `when`(interactor.getAveragePropertyPrice()).thenReturn(Single.error(Exception("any error")))

        viewModel.getAveragePropertiesPrice()
        verify(stateObserver).onChanged(
            MainViewState(isLoading = false, averagePrice = null)
        )
    }

    companion object {
        private const val average = "123456.98"
        private val successResult = Result.Success(AveragePrice(average))
    }
}