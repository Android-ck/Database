package com.zerir.database

import android.app.Application
import com.zerir.database.db.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        //To create and open the database when app launch
        CoroutineScope(Dispatchers.IO).launch {
            AppDatabase.getInstance(this@App)
        }
    }

}