package com.example.newsappdagger2.recourse

import com.example.newsappdagger2.classses.category.SearchByCategory
import com.example.newsappdagger2.classses.newapi.Article
import com.example.newsappdagger2.classses.newapi.NewApiMYaCCOUNT
import com.example.newsappdagger2.databace.entity.UserEntity

sealed class UserResource {

    object Loading : UserResource()

    data class Success(var list: List<Article>?) : UserResource()

    data class Error(val message: String) : UserResource()

}
