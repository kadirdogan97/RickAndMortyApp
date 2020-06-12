package com.kadirdogan97.rickandmortyapp

import android.app.Application
import com.kadirdogan97.rickandmortyapp.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

internal class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }


    private fun initKoin(){
        val modules= listOf(databaseModules, viewModelModules, dataSourceModules, repositoryModules, networkModules, useCaseModules)
        startKoin{
            androidContext(this@MyApplication)
            modules(modules)
        }

    }
}