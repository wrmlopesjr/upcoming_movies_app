package com.arctouch.codechallenge.base

import com.arctouch.codechallenge.api.TmdbRepository
import com.arctouch.codechallenge.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module


val viewModelModules = module {
    viewModel { HomeViewModel(get()) }
}

val serviceModules = module {
    single { ApiService() }
}

val repositoryModules = module {
    single { TmdbRepository(get()) }
}

val appComponent: List<Module> = listOf(viewModelModules, serviceModules, repositoryModules)