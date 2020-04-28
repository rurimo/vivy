package com.benallouch.vivy.di

import com.benallouch.vivy.view.doctors.DoctorsFragmentViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { DoctorsFragmentViewModel(get()) }
}
