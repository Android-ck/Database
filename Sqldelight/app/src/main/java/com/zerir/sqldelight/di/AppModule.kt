package com.zerir.sqldelight.di

import android.app.Application
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import com.zerir.sqldelight.AppDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSqlDriver(application: Application): SqlDriver {
        return AndroidSqliteDriver(
            schema = AppDB.Schema,
            context = application,
            name = "app.db"
        )
    }

    @Provides
    @Singleton
    fun provideAppDatabase(driver: SqlDriver): AppDB {
        return AppDB(driver)
    }
}