package com.ka.courierka.di

import android.content.Context
import com.ka.courierka.MainActivity
import dagger.BindsInstance
import dagger.Component

@Component(modules = [DataModule::class, AppModule::class])
internal interface AppComponent {
    fun inject(mainActivity: MainActivity)

    @Component.Factory
    interface Factory{

        fun create(
            @BindsInstance
            context: Context
        ):AppComponent
    }
}