package zar1official.simplenote.di

import org.koin.dsl.module
import zar1official.simplenote.data.repositories.NoteRepositoryImpl
import zar1official.simplenote.domain.repositories.NoteRepository
import zar1official.simplenote.domain.usecases.*

val domainModule = module {
    single<NoteRepository> {
        return@single NoteRepositoryImpl(
            noteDao = get(),
            noteService = get(),
            mapper = get(),
            networkMapper = get()
        )
    }

    factory<LoadNoteUseCase> {
        LoadNoteUseCase(repository = get())
    }

    factory<FindNoteByIdUseCase> {
        FindNoteByIdUseCase(repository = get())
    }

    factory<UpdateNoteUseCase> {
        UpdateNoteUseCase(repository = get())
    }

    factory<SaveNoteUseCase> {
        SaveNoteUseCase(repository = get())
    }

    factory<RemoveNoteUseCase> {
        RemoveNoteUseCase(repository = get())
    }

    factory<GetAllNotesUseCase> {
        GetAllNotesUseCase(repository = get())
    }


}