package com.yxhelzvl.umsnh_id.core.di

import com.yxhelzvl.umsnh_id.features.home.network.HomeRepository
import com.yxhelzvl.umsnh_id.features.home.network.HomeRepositoryImpl
import com.yxhelzvl.umsnh_id.features.home.network.HomeService
import com.yxhelzvl.umsnh_id.features.home.network.HomeServiceImpl
import com.yxhelzvl.umsnh_id.features.home.ui.HomeViewModel
import com.yxhelzvl.umsnh_id.features.login.data.remote.AuthApiService
import com.yxhelzvl.umsnh_id.features.login.data.remote.AuthApiServiceImpl
import com.yxhelzvl.umsnh_id.features.login.ui.LoginViewModel
import com.yxhelzvl.umsnh_id.features.qrcreate.data.remote.QrInfoApiService
import com.yxhelzvl.umsnh_id.features.qrcreate.data.remote.QrInfoApiServiceImpl
import com.yxhelzvl.umsnh_id.features.qrcreate.ui.QrCreateScreen
import com.yxhelzvl.umsnh_id.features.qrcreate.ui.QrCreateViewModel
import com.yxhelzvl.umsnh_id.features.register.data.remote.RegisterRepository
import com.yxhelzvl.umsnh_id.features.register.data.remote.RegisterRepositoryImpl
import com.yxhelzvl.umsnh_id.features.register.data.remote.RegisterService
import com.yxhelzvl.umsnh_id.features.register.data.remote.RegisterServiceImpl
import com.yxhelzvl.umsnh_id.features.register.ui.RegisterViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {


    single<RegisterService> { RegisterServiceImpl(get()) }
    single<RegisterRepository> { RegisterRepositoryImpl(get()) }

    single<HomeService> { HomeServiceImpl(get()) }
    single<HomeRepository> { HomeRepositoryImpl(get()) }

    single<AuthApiService> { AuthApiServiceImpl(get()) }

    single<QrInfoApiService>{ QrInfoApiServiceImpl(get()) }

    viewModel { RegisterViewModel(get()) }
    viewModel { LoginViewModel(get(),get())}
    viewModel { HomeViewModel(get(),get())}
    viewModel { QrCreateViewModel(get(),get()) }
}