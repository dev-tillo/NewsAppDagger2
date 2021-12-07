package com.example.newsappdagger2.networking

import com.example.newsappdagger2.classses.newapi.NewApiMYaCCOUNT
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServise {

    @GET("everything?from=2021-11-27&sortBy=popularity&apiKey=023eab991fa74b7588d7c3620f65c08f")
    suspend fun getCategory(
        @Query("q") q: String = "Apple"
    ): Response<NewApiMYaCCOUNT>

//    023eab991fa74b7588d7c3620f65c08f yangi

//    a40b77204836439eaa4053e117c9d152 eski

//    7968c200cbf043618fbff878682c54ce 3 apikey
}