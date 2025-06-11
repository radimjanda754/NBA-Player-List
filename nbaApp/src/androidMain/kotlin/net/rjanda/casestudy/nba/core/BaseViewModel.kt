package net.rjanda.casestudy.nba.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import net.rjanda.casestudy.nba.core.model.data.ContentState

/**
 * Simple MVVM view model that every view model should inherit from.
 * Contains few basic functions that can be used for handling simple logic outside of UseCases
 */
open class BaseViewModel<T : Any>(initialState: ContentState<T> = ContentState.Loading) : ViewModel() {

    private val _state: MutableStateFlow<ContentState<T>> =
        MutableStateFlow(initialState)
    val state: StateFlow<ContentState<T>> = _state

    /**
     * Extension function to handle simple observing logic and the state.
     */
    fun Flow<T>.observeInState(dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        viewModelScope.launch(dispatcher) {
            collect { _state.update { _ -> ContentState.Success(it) } }
        }
    }

    /**
     * Extension function to execute a simple logic and handle result inside state.
     * Optionally sets the state to Loading before executing the block.
     * Updates the state to Error if an exception occurs.
     */
    fun launchInState(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        setLoading: Boolean = false,
        block: suspend () -> Unit
    ) {
        viewModelScope.launch(dispatcher) {
            try {
                if (setLoading) {
                    _state.update { ContentState.Loading }
                }
                block()
            } catch (e: Exception) {
                e.printStackTrace()
                _state.update { ContentState.Error(e) }
            }
        }
    }

}