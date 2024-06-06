package com.ka.courierka.coin

import android.app.Application
import com.ka.courierka.coin.repo.Repository
import com.ka.courierka.coin.repo.RepositoryImpl
import com.ka.courierka.coin.repo.TypeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TypeApp : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TypeApp)
            modules(appModule)
        }
    }
    val appModule = module {
        // область видимости синглтона
        single {
            Retrofit.Builder()
                .baseUrl("https://my-json-server.typicode.com/ArtemKova/fjsonforme/")
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