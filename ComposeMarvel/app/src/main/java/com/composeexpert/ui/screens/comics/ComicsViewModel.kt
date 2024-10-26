package com.composeexpert.ui.screens.comics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.composeexpert.data.entities.Comic
import com.composeexpert.data.network.entities.Result
import com.composeexpert.data.repositories.ComicsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ComicsViewModel : ViewModel() {

    val state = Comic.Format.entries.associateWith { MutableStateFlow(UIState()) }

    fun formatRequested(format: Comic.Format) {
        val uiState = state.getValue(format)
        val comics = uiState.value.comics
        if (comics is Either.Right && comics.value.isEmpty()) {
            viewModelScope.launch {
                uiState.value = UIState(isLoading = true)
                uiState.value = UIState(comics = ComicsRepository.get(format))
            }
        }
    }

    data class UIState(
        val isLoading: Boolean = false,
        val comics: Result<List<Comic>> = Either.Right(emptyList()),
    )
}