package cz.sandera.letsmeet.presentation.util

sealed class UiEvent {
    object NavigateTo: UiEvent()
    object NavigateUp: UiEvent()
    data class ShowSnackbar(val message: UiText): UiEvent()
    data class ShowDialog(val message: UiText): UiEvent()
}
