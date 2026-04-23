package com.example.userdraftapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.userdraftapp.data.models.LockedStatus

@Entity(
    tableName = "drafts_table"
)
data class DraftEntity(
    @PrimaryKey(autoGenerate = true) val draftId: Int = 0,
    val userId: String,
    val title: String,
    val description: String,
    val lockedStatus: LockedStatus
)


