package com.example.newsappdagger2.databace.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.newsappdagger2.databace.entity.UserEntity

@Dao
interface NewsDao {

    @Insert(onConflict = REPLACE)
    fun addArticle(userEntity: UserEntity)

    @Insert(onConflict = REPLACE)
    suspend fun addListArticle(list: List<UserEntity>)

    @Query("select *from userentity")
    fun getList(): List<UserEntity>

    @Query("delete from userentity where author=:v")
    fun deletBYId(v: String)

}