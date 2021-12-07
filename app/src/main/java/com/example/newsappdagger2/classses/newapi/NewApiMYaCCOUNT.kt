package com.example.newsappdagger2.classses.newapi

data class NewApiMYaCCOUNT(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)