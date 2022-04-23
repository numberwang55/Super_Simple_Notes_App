package com.example.composetodoapp_myattempt5.di

import android.app.Application
import androidx.room.Room
import com.example.composetodoapp_myattempt5.data.NoteDatabase
import com.example.composetodoapp_myattempt5.data.NoteRepository
import com.example.composetodoapp_myattempt5.data.NoteRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            "note_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.dao)
    }
}