package com.ka.courierka.coin

import com.ka.courierka.coin.repo.Repository
import com.ka.courierka.coin.repo.RepositoryImpl
import com.ka.courierka.coin.repo.TypeViewModel
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