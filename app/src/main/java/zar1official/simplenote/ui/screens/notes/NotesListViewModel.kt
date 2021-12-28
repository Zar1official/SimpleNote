package zar1official.simplenote.ui.screens.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import zar1official.simplenote.domain.Note
import zar1official.simplenote.domain.NoteRepository

class NotesListViewModel(private val repository: NoteRepository) : ViewModel() {
    val allNotes: LiveData<List<Note>>
        get() = repository.getNotes().asLiveData()
}