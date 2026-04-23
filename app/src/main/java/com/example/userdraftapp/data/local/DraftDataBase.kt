package com.example.userdraftapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DraftEntity::class], version = 1)
abstract class DraftDataBase: RoomDatabase() {

    abstract fun getDraftsDao(): DraftsDao
}