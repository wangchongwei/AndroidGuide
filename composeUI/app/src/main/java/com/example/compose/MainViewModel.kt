package com.example.compose

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    var uiState by mutableStateOf<ExampleUiState>(ExampleUiState())
        private set

    fun somethingRelatedToBusinessLogic() {
        uiState.userMessages = listOf("1", "2", "3")
        uiState.dataToDisplayOnScreen = listOf("1", "2", "3")
    }



}