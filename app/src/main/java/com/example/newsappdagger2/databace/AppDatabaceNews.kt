package com.example.newsappdagger2.databace

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newsappdagger2.databace.dao.NewsDao
import com.example.newsappdagger2.databace.entity.UserEntity


@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabaceNews : RoomDatabase() {

    abstract fun newsDao() : NewsDao

}