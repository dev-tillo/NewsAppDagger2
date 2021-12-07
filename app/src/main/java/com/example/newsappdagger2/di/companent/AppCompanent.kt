package com.example.newsappdagger2.di.companent

import com.example.newsappdagger2.di.module.DatabaceModule
import com.example.newsappdagger2.di.module.NetworkModule
import com.example.newsappdagger2.fragments.Info
import com.example.newsappdagger2.fragments.Info2Db
import com.example.newsappdagger2.fragments.SearchFragment
import com.example.newsappdagger2.fragments.Slide
import com.example.newsappdagger2.fragments.bottom.Home
import com.example.newsappdagger2.fragments.bottom.ReadDb
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DatabaceModule::class])
interface AppCompanent {

    fun inject(home: Home)

    fun injectslide(slide: Slide)

    fun injectdb(readDb: ReadDb)

    fun inject(info: Info)

    fun injectDbInfo2(info2Db: Info2Db)

    fun injectsearch(searchFragment: SearchFragment)

}