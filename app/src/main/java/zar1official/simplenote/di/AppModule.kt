package zar1official.simplenote.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import zar1official.simplenote.ui.main.MainViewModel
import zar1official.simplenote.ui.screens.about.AboutViewModel
import zar1official.simplenote.ui.screens.about.cords.CordsViewModel
import zar1official.simplenote.ui.screens.about.webview.WebViewFragmentViewModel
import zar1official.simplenote.ui.screens.creating.CreatingNoteViewModel
import zar1official.simplenote.ui.screens.creating.dialog.ConfirmCreatingViewModel
import zar1official.simplenote.ui.screens.notes.NotesListViewModel

val appModule = module {
    viewModel {
        MainViewModel()
    }

    viewModel {
        ConfirmCreatingViewModel(
            findNoteByIdUseCase = get(),
            updateNoteUseCase = get(),
            saveNoteUseCase = get()
        )
    }

    viewModel {
        NotesListViewModel(
            getAllNotesUseCase = get(),
            removeNoteUseCase = get()
        )
    }

    viewModel { params ->
        CreatingNoteViewModel(
            loadNoteUseCase = get(),
            currentNote = params.get()
        )
    }

    viewModel {
        AboutViewModel()
    }

    viewModel {
        WebViewFragmentViewModel()
    }

    viewModel {
        CordsViewModel()
    }
}

