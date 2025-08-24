package com.example.notes2025.data.local.database.di

import android.content.Context
import androidx.room.Room
import com.example.notes2025.data.local.database.NotesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun providesNotesDatabase(
        @ApplicationContext context: Context,
    ): NotesDatabase =
        Room
            .databaseBuilder(
                context,
                NotesDatabase::class.java,
                "notes-database",
            ).build()

    @Provides
    fun providesNoteDao(database: NotesDatabase) = database.noteDao()
}
