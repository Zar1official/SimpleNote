package zar1official.simplenote.ui.screens.creating

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import zar1official.simplenote.domain.Note
import zar1official.simplenote.domain.NoteRepository

class CreatingNoteViewModelFactory(
    private val repository: NoteRepository,
    private val currentNote: Note = Note()
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CreatingNoteViewModel(repository, currentNote) as T
}