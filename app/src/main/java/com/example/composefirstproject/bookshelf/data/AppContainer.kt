package com.example.composefirstproject.bookshelf.data

import com.example.composefirstproject.bookshelf.network.BookServices
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


interface AppContainer {
    val booksRepository: BooksRepository
}

class DefaultAppContainer : AppContainer {
    private val BASE_URL = "https://www.googleapis.com/books/v1/"


    private val retrafit: Retrofit =
        Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL)
            .build()


    private val retrofitServices: BookServices by lazy {
        retrafit.create(BookServices::class.java)
    }
    override val booksRepository: BooksRepository by lazy {
        NetworkBooksRepository(retrofitServices)
    }


}