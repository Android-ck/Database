package com.zerir.database.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zerir.database.entitites.*
import com.zerir.database.entitites.relations.StudentsSubjectsCrossRef

@Database(
    version = 1,
    entities = [
        School::class,
        Director::class,
        Student::class,
        Subject::class,
        Visitor::class,
        StudentsSubjectsCrossRef::class,
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract val dao: Dao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase = synchronized(this) {
            return INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app-db"
            ).build().also { INSTANCE = it }
        }
    }

}