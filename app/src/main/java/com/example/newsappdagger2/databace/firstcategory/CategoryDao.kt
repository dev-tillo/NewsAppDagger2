package com.example.newsappdagger2.databace.firstcategory

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import retrofit2.http.GET


@Dao
interface CategoryDao {

    @Insert(onConflict = REPLACE)
    fun addDbCategory(categoryEntity: CategoryEntity)

    @Query("delete from category where id=:v")
    fun deletBYId(v: Int)

    @Query("select *from category")
    fun getLIst(): List<CategoryEntity>

}