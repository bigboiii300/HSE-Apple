package hseapp.app

import android.app.Application
import hseapp.app.data.di.networkModule
import hseapp.app.data.di.trashModule
import hseapp.app.data.preferences.Prefs
import hseapp.app.utils.FakeDataCreator
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Приложение, просто инициализирует разные штуки
 */
class HSEApp : Application() {
    
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(trashModule, networkModule)
        }
        if (Prefs.launchedFirstTime) FakeDataCreator.createFakeData()
    }
}