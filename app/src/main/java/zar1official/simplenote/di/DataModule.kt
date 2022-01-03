package zar1official.simplenote.di

import org.koin.dsl.module
import retrofit2.Retrofit
import zar1official.simplenote.data.database.NoteDao
import zar1official.simplenote.data.database.NoteDatabase
import zar1official.simplenote.data.mappers.NetworkNoteMapper
import zar1official.simplenote.data.mappers.NoteMapper
import zar1official.simplenote.data.network.Retrofit2Client
import zar1official.simplenote.data.network.service.NoteService

private fun provideNoteService(retrofit: Retrofit): NoteService =
    retrofit.create(NoteService::class.java)

private fun provideNoteDao(noteDataBase: NoteDatabase): NoteDao = noteDataBase.noteDao()

val dataModule = module {
    single<NoteDatabase> {
        NoteDatabase.getDatabase(context = get())
    }

    single<Retrofit> {
        Retrofit2Client.getInstance()
    }

    single<NoteDao> {
        provideNoteDao(noteDataBase = get())
    }

    single<NoteService> {
        provideNoteService(retrofit = get())
    }

    factory<NoteMapper> {
        NoteMapper()
    }

    factory<NetworkNoteMapper> {
        NetworkNoteMapper()
    }
}