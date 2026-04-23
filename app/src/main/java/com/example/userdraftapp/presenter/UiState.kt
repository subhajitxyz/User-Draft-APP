package com.example.userdraftapp.presenter

import com.example.userdraftapp.data.local.DraftEntity

sealed class UiState {
    object Loading: UiState()
    data class Success(val data: DraftEntity): UiState()
    data class Error(val e: String): UiState()
}