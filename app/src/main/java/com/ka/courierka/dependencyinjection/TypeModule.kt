package com.ka.courierka.dependencyinjection

import android.content.Context
import com.ka.courierka.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Named
import javax.inject.Singleton
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
class TypeModule {

    @Singleton
    @Provides
    @Named("TypeStringFirst")
    fun providingType() = TypeOrder("0", "food")
    @Singleton
    @Provides
    @Named("TypeStringSecond")
    fun providingType1(@ApplicationContext context: Context): TypeOrder =
        TypeOrder(context.getString(R.string.order1), context.getString(R.string.order2))


}