package com.example.userdraftapp.data.repo

import com.example.userdraftapp.data.local.DraftEntity
import com.example.userdraftapp.data.local.DraftsDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserDraftsRepository @Inject constructor(
    private val draftsDao: DraftsDao
) {

    fun getAllDraftsOfUserFlow(userId: String): Flow<List<DraftEntity>> {
        return draftsDao.getUserAllDraftsFlow(userId)
    }

    fun getAllDraftsOfUser(userId: String): List<DraftEntity> {
        return draftsDao.getUserAllDrafts(userId)
    }

    fun getDraft(draftId: Int): DraftEntity {
        return draftsDao.getDraft(draftId)
    }


    suspend fun insertUserDraft(draft: DraftEntity) {
        draftsDao.insertUserDraft(draft)
    }

//    suspend fun editUserDraft(draft: DraftEntity) {
//        draftsDao.editUserDraft(draft)
//    }
}