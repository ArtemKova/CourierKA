package com.ka.courierka.di

import com.ka.courierka.di.repo.Repository
import com.ka.courierka.di.repo.RepositoryImpl
import com.ka.courierka.di.repo.TypeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppModule {
    val appModule = module {
        // область видимости синглтона
        single {
            Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Apifortype::class.java)
        }

        single<Repository> {
            RepositoryImpl(get())
        }

        viewModel {
            TypeViewModel(get())
        }
    }
}