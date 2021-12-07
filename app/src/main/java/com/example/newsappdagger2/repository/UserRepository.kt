package com.example.newsappdagger2.repository

import com.example.newsappdagger2.databace.dao.NewsDao
import com.example.newsappdagger2.databace.entity.UserEntity
import com.example.newsappdagger2.networking.ApiServise
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiServise: ApiServise,
    private val newsDao: NewsDao
) {

    suspend fun getUsers() = flow { emit(apiServise.getCategory()) }

    suspend fun getCategory(string: String) = flow { emit(apiServise.getCategory(string)) }

    //    db

    suspend fun addUsers(list: List<UserEntity>) = newsDao.addListArticle(list)

    suspend fun getDbUsers() = flow { emit(newsDao.getList()) }

}