package com.example.composefirstproject

import android.app.Application
import com.example.composefirstproject.bookshelf.data.AppContainer
import com.example.composefirstproject.bookshelf.data.DefaultAppContainer

class BooksApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        container = DefaultAppContainer()
        super.onCreate()
    }
}