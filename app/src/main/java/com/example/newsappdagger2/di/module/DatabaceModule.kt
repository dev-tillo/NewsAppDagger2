package com.example.newsappdagger2.di.module

import android.content.Context
import androidx.room.Room
import com.example.newsappdagger2.databace.AppDatabaceNews
import com.example.newsappdagger2.databace.dao.NewsDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaceModule(var context: Context) {

    @Provides
    @Singleton
    fun provideContext(): Context = context

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabaceNews {
        return Room.databaseBuilder(context, AppDatabaceNews::class.java, "author_db")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsDao(appDatabase: AppDatabaceNews):NewsDao = appDatabase.newsDao()

}