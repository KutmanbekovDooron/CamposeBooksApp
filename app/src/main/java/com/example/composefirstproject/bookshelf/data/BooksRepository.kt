package com.example.composefirstproject.bookshelf.data

import com.example.composefirstproject.bookshelf.network.BookServices

interface BooksRepository {
    suspend fun getBooks(query: String, maxResults: Int): List<Book>
}

class NetworkBooksRepository(
    private val bookServices: BookServices
) : BooksRepository {
    override suspend fun getBooks(query: String, maxResults: Int): List<Book> =
        bookServices.bookSearch(query, maxResults).items.map { items ->
            Book(
                title = items.volumeInfo?.title,
                previewLink = items.volumeInfo?.previewLink,
                imageLink = items.volumeInfo?.imageLinks?.thumbnail
            )
        }

}