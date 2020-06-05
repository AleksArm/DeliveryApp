package com.highestaim.deliveryapp.DI


import com.highestaim.deliveryapp.repository.OrdersRepository
import com.highestaim.viewmodel.OrdersViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val appRepositories: Module = module {
    single { OrdersRepository() }
}


val appViewModels: Module = module {
    viewModel { OrdersViewModel(get()) }
}