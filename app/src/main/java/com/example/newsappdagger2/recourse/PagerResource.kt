package com.example.newsappdagger2.recourse

import com.example.newsappdagger2.classses.category.SearchByCategory
import com.example.newsappdagger2.classses.newapi.Article
import com.example.newsappdagger2.classses.newapi.NewApiMYaCCOUNT
import com.example.newsappdagger2.databace.entity.UserEntity

sealed class PagerResource {

    object Loading : PagerResource()

    data class Success(var list: List<Article>?) : PagerResource()

    data class Error(val message: String) : PagerResource()

}