package com.diegopizzo.rightmovetechtest.ui.config

import com.diegopizzo.rightmovetechtest.ui.MainViewModel
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get(), CompositeDisposable()) }
}