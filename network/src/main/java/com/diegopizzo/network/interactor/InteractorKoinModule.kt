package com.diegopizzo.network.interactor

import org.koin.dsl.module

val interactorModule = module {
    factory<IPropertiesInteractor> {
        PropertiesInteractor(get(), get())
    }
}