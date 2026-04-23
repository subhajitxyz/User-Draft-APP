package com.example.userdraftapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.userdraftapp.data.local.DraftEntity
import com.example.userdraftapp.data.models.LockedStatus
import com.example.userdraftapp.data.repo.UserDraftsRepository
import com.example.userdraftapp.presenter.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DraftViewModel @Inject constructor(
    private val draftsRepository: UserDraftsRepository
): ViewModel() {

    val _currentDraft = MutableStateFlow<UiState>(UiState.Loading)
    val currentDraft: StateFlow<UiState> = _currentDraft

    fun loadDrafts (draftId: Int) {
        viewModelScope.launch {
            try {
                val draft = withContext(Dispatchers.IO) {
                    draftsRepository.getDraft(draftId)
                }
                _currentDraft.value = UiState.Success(draft)
            } catch (e: Exception) {
                _currentDraft.value = UiState.Error("Something Went Error.")
            }
        }
    }

    fun addUserDraft(draftId: Int, currentUserId: String, title: String, des: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val draft = DraftEntity(
                    userId = currentUserId,
                    title = title,
                    description = des,
                    lockedStatus = LockedStatus.EDITABLE
                )
                draftsRepository.insertUserDraft(draft)
                loadDrafts(draftId)
            } catch (e: Exception) {
                Log.d("testuser", e.toString())
            }

        }
    }
}