package zar1official.simplenote.di

import org.koin.dsl.module
import zar1official.simplenote.data.repositories.NoteRepositoryImpl
import zar1official.simplenote.domain.NoteRepository

val domainModule = module {
    single<NoteRepository> {
        return@single NoteRepositoryImpl(
            noteDao = get(),
            noteService = get(),
            mapper = get(),
            networkMapper = get()
        )
    }
}