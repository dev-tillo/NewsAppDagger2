package com.example.newsappdagger2.classses.category

data class SearchByCategory(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)