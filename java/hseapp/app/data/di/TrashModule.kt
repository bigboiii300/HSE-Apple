package hseapp.app.data.di

import androidx.room.Room
import hseapp.app.data.db.HSEDatabase
import hseapp.app.data.source.core.ApiMessagesDataSource
import hseapp.app.data.source.core.ApiNotificationsDataSource
import hseapp.app.data.source.impl.FakeApiMessagesDataSource
import hseapp.app.data.source.impl.FakeApiNotificationsDataSource
import org.koin.dsl.module

/**
 * Dependency Injection для базы данных и фейковых датасорсов (чтобы можно потом было
 * заменить на нормальные)
 */
val trashModule = module {
    single {
        Room.databaseBuilder(get(), HSEDatabase::class.java, "fake-database").build()
    }
    single<ApiNotificationsDataSource> { FakeApiNotificationsDataSource() }
    single<ApiMessagesDataSource> { FakeApiMessagesDataSource() }
}