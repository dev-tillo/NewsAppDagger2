package com.example.newsappdagger2.databace.firstcategory

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CategoryEntity::class], version = 1)
abstract class AppDatabaceCategory : RoomDatabase() {

    abstract fun categorydb(): CategoryDao

    companion object {
        private var appDatabaceCategory: AppDatabaceCategory? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabaceCategory {
            if (appDatabaceCategory == null) {
                appDatabaceCategory =
                    Room.databaseBuilder(context, AppDatabaceCategory::class.java, "mycategory")
                        .allowMainThreadQueries()
                        .build()
            }
            return appDatabaceCategory!!
        }
    }
}