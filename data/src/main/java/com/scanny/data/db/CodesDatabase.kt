package com.scanny.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.scanny.data.entities.CodeEntity

@Database(entities = [CodeEntity::class], version = 1)
abstract class CodesDatabase : RoomDatabase() {

    abstract fun codesDao(): CodesDAO

    companion object {
        @Volatile
        private var INSTANCE: CodesDatabase? = null

        fun getDatabase(appContext: Context): CodesDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    appContext,
                    CodesDatabase::class.java,
                    CodesDatabase::class.simpleName!!
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }

    }


}