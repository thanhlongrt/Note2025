package com.example.notes2025.data.di

import com.example.notes2025.data.repository.DefaultNoteRepository
import com.example.notes2025.data.repository.NoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    @Singleton
    abstract fun bindNoteRepository(noteRepository: DefaultNoteRepository): NoteRepository
}
