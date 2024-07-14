package com.ka.courierka.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides

    fun provideTypeOrderService(): Apifortype {
        return Apifortype.create()
    }
}
//        single<Repository> {
//            RepositoryImpl(get())
//        }
//
//        viewModel {
//            TypeViewModel(get())
//        }
//    }
