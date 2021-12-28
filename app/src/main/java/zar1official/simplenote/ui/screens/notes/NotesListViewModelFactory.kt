package zar1official.simplenote.ui.screens.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import zar1official.simplenote.domain.NoteRepository

class NotesListViewModelFactory(private val repository: NoteRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        NotesListViewModel(repository) as T
}