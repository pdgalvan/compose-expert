package com.composeexpert.ui.screens.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.composeexpert.data.entities.Character
import com.composeexpert.data.network.entities.Result
import com.composeexpert.data.repositories.CharactersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharactersViewModel : ViewModel() {
    private val _state = MutableStateFlow(UIState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update { UIState(isLoading = true) }
            _state.update { UIState(characters = CharactersRepository.get()) }
        }
    }

    data class UIState(
        val isLoading: Boolean = false,
        val characters: Result<List<Character>> = Either.Right(emptyList()),
    )
}