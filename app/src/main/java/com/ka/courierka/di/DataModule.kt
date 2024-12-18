package com.ka.courierka.di

import android.content.Context
import com.example.data.data.AppDataBase
import com.example.data.data.OrderListDao
import com.example.data.data.OrderListRepositoryImpl
import com.example.data.domain.OrderListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun provideDb(@ApplicationContext context: Context): AppDataBase {
        return AppDataBase.getInstance(context)
    }

    @Provides
    fun provideOrderListDao(appDataBase: AppDataBase): OrderListDao {
        return appDataBase.orderListDao()
    }

    @Provides
    fun provideOrderListRepository(orderListDao: OrderListDao): OrderListRepository {
        return OrderListRepositoryImpl(orderListDao)
    }

}