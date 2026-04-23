package com.example.userdraftapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DraftsDao {

//    @Query("SELECT * FROM drafts_table WHERE userId = :userId")
//    fun getUserAllDrafts(userId: String): Flow<List<DraftEntity>>

    @Query("SELECT * FROM drafts_table WHERE userId = :userId")
    fun getUserAllDrafts(userId: String): List<DraftEntity>

    @Query("SELECT * FROM drafts_table WHERE draftId = :draftId")
    fun getDraft(draftId: Int): DraftEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserDraft(draft: DraftEntity)

}