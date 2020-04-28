package com.benallouch.vivy.di

import com.benallouch.vivy.repository.DoctorsRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { DoctorsRepository(get()) }
}