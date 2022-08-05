package com.example.compose

data class ExampleUiState(
    var dataToDisplayOnScreen: List<String> = emptyList(),
    var userMessages: List<String> = emptyList(),
    val loading: Boolean = false
)
