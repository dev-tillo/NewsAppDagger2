package com.example.newsappdagger2

import android.app.Application
import com.example.newsappdagger2.di.companent.AppCompanent
import com.example.newsappdagger2.di.companent.DaggerAppCompanent
import com.example.newsappdagger2.di.module.DatabaceModule
import com.yariksoffice.lingver.Lingver

class App : Application() {


    companion object {
        lateinit var appCompanent: AppCompanent
    }

    override fun onCreate() {
        super.onCreate()
        Lingver.init(this, "de")
        appCompanent = DaggerAppCompanent.builder()
            .databaceModule(DatabaceModule(applicationContext))
            .build()

    }
}