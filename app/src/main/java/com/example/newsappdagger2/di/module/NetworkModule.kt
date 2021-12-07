package com.example.newsappdagger2.di.module

import android.content.Context
import com.example.newsappdagger2.BuildConfig
import com.example.newsappdagger2.networking.ApiServise
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideBaseUrl(): String = "https://newsapi.org/v2/"

    @Provides
    @Singleton
    fun providesGsonCOnvertory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideloginIntersepter(): HttpLoggingInterceptor {
        val hhtploginintersepter = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            hhtploginintersepter.level = HttpLoggingInterceptor.Level.BODY
        } else {
            hhtploginintersepter.level = HttpLoggingInterceptor.Level.NONE
        }
        return hhtploginintersepter
    }

    @Provides
    @Singleton
    fun providesOkhttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        baseUrl: String,
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): ApiServise = retrofit.create(ApiServise::class.java)

//    @Provides
//    @Singleton
//    fun provideApp(context: Context) xoxlasam qo`sha olaman nima kerak bo`lsa davomiga

}