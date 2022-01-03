package zar1official.simplenote.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import zar1official.simplenote.ui.main.MainViewModel
import zar1official.simplenote.ui.screens.creating.CreatingNoteViewModel
import zar1official.simplenote.ui.screens.creating.dialog.ConfirmCreatingViewModel
import zar1official.simplenote.ui.screens.notes.NotesListViewModel

val appModule = module {
    viewModel {
        MainViewModel()
    }

    viewModel {
        ConfirmCreatingViewModel(repository = get())
    }

    viewModel {
        NotesListViewModel(repository = get())
    }

    viewModel { params ->
        CreatingNoteViewModel(repository = get(), currentNote = params.get())
    }
}

