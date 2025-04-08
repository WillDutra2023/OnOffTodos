package br.com.william.onofftodos

import android.app.Application
import br.com.william.onofftodos.data.di.androidModule
import br.com.william.onofftodos.data.di.networkModule
import br.com.william.onofftodos.data.di.repositories
import br.com.william.onofftodos.data.di.serviceHelperModule
import br.com.william.onofftodos.data.di.storageModule
import br.com.william.onofftodos.domain.di.useCaseModule
import br.com.william.onofftodos.domain.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class MyApplication : Application(), KoinComponent {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            androidLogger()
            modules(
                storageModule,
                repositories,
                serviceHelperModule,
                networkModule,
                useCaseModule,
                viewModelModule,
                androidModule
            )
            workManagerFactory()
        }

    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }
}