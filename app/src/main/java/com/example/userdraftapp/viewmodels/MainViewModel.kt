package com.example.userdraftapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.userdraftapp.data.local.DraftEntity
import com.example.userdraftapp.data.models.LockedStatus
import com.example.userdraftapp.data.repo.UserDraftsRepository
import com.example.userdraftapp.data.repo.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import java.security.PrivateKey
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val userDraftsRepository: UserDraftsRepository
): ViewModel() {

    val userList = userRepository.getUsersList()

    val currentUserId = MutableStateFlow<String>("user_1")

//    val userDraftList = userDraftsRepository.getAllDraftsOfUser(currentUserId.value)
//        .stateIn(
//            viewModelScope,
//            SharingStarted.WhileSubscribed(5000),
//            emptyList()
//        )


    val drafts: StateFlow<List<DraftEntity>> =
        currentUserId
            .flatMapLatest { userId ->
                userDraftsRepository.getAllDraftsOfUserFlow(userId) // Flow from Room
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )


    val userDrafts = MutableStateFlow<List<DraftEntity>>(emptyList())

    fun loadUserDrafts() {
        viewModelScope.launch {
            try {
                val drafts  = withContext(Dispatchers.IO) {
                    userDraftsRepository.getAllDraftsOfUser(currentUserId.value)
                }
                userDrafts.value = drafts
            } catch (e: Exception) {

            }
        }
    }




    fun loginUser(userid: String) {
        currentUserId.value = userid
        loadUserDrafts()
    }


    fun addDemoUserDraft() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val draft = DraftEntity(
                    userId = currentUserId.value,
                    title = "deo title",
                    description = "ddafa",
                    lockedStatus = LockedStatus.EDITABLE
                )
                userDraftsRepository.insertUserDraft(draft)
            } catch (e: Exception) {
                Log.d("testuser", e.toString())
            }

        }
    }
}