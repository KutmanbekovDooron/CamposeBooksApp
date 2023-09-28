package com.example.composefirstproject.ui.theme

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import coil.network.HttpException
import com.example.composefirstproject.BooksApplication
import com.example.composefirstproject.bookshelf.data.Book
import com.example.composefirstproject.bookshelf.data.BooksRepository
import kotlinx.coroutines.launch

sealed interface BooksUiState {
    data class Success(val bookSearch: List<Book>) : BooksUiState
    object Error : BooksUiState
    object Loading : BooksUiState
}

class BooksViewModel(
    private val booksRepository: BooksRepository
) : ViewModel() {

    var booksUiState: BooksUiState by mutableStateOf(BooksUiState.Loading)
        private set

    private val _searchWidgetState: MutableState<SearchWidgetState> =
        mutableStateOf(value = SearchWidgetState.CLOSED)
    val searchWidgetState: State<SearchWidgetState> = _searchWidgetState


    private val _searchTextState: MutableState<String> = mutableStateOf(value = "")
    val searchTextState: State<String> = _searchTextState

    fun updateSearchWidgetState (newValue: SearchWidgetState){
        _searchWidgetState.value = newValue;
    }

    fun updateSearchTextState (newValue: String){
        _searchTextState.value = newValue;
    }

    init {
        getBooks()
    }

    fun getBooks(query: String = "book", maxResult: Int = 40) {
        viewModelScope.launch {
            booksUiState = BooksUiState.Loading

            booksUiState = try {
                BooksUiState.Success(booksRepository.getBooks(query, maxResult))
            } catch (
                e: HttpException
            ) {
                BooksUiState.Error
            }

        }
    }


    companion object {
        var Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {

                val application = (this[APPLICATION_KEY] as BooksApplication)
                val booksRepository = application.container.booksRepository
                BooksViewModel(booksRepository = booksRepository)
            }
        }
    }
}

enum class SearchWidgetState {
    OPENED,
    CLOSED
}