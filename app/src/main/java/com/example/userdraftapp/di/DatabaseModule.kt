package com.example.userdraftapp.di

import android.content.Context
import androidx.room.Room
import com.example.userdraftapp.data.local.DraftDataBase
import com.example.userdraftapp.data.local.DraftsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesDraftsDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            DraftDataBase::class.java,
            "user_drafts_db"
        ).build()

    @Provides
    @Singleton
    fun providesDraftDao(draftDataBase: DraftDataBase): DraftsDao {
        return draftDataBase.getDraftsDao()
    }

}
