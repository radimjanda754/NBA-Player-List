package net.rjanda.casestudy.nba.core.model.data

/**
 * A sealed class representing the state of content in the application.
 * It is used to manage and communicate the current state of data operations
 * (e.g., loading, success, or error) between the ViewModel and the UI.
 */
sealed class ContentState<out T> {

    /**
     * Represents a loading state, typically used when a data operation is in progress.
     */
    data object Loading : ContentState<Nothing>()

    /**
     * Represents a successful state with the associated data.
     * @param data The data retrieved from the operation.
     */
    data class Success<T : Any>(val data: T) : ContentState<T>()

    /**
     * Represents an error state with the associated exception.
     * @param exception The exception that occurred during the operation.
     */
    data class Error(val exception: Exception) : ContentState<Nothing>()

    /**
     * Represents an empty state. Contains no data.
     */
    data object Empty : ContentState<Nothing>()

    fun isLoadingOrSuccess(): Boolean {
        return this is Loading || this is Success<*>
    }

}