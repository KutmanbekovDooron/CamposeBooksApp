package com.example.composefirstproject.bookshelf.network

import com.example.composefirstproject.bookshelf.network.model.BookShelf
import retrofit2.http.GET
import retrofit2.http.Query

interface BookServices {

    @GET("volumes")
    suspend fun bookSearch(
        @Query("q") searchQuery: String,
        @Query("maxResults") macResult: Int
    ) : BookShelf

}