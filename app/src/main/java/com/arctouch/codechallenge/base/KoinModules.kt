package com.arctouch.codechallenge.base

import org.koin.core.module.Module
import org.koin.dsl.module


val viewModelModules = module {
}

val serviceModules = module {
}

val repositoryModules = module {
}

val appComponent: List<Module> = listOf(viewModelModules, serviceModules, repositoryModules)