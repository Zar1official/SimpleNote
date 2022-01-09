package zar1official.simplenote.ui.screens.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import zar1official.simplenote.domain.Note
import zar1official.simplenote.domain.NoteRepository
import zar1official.simplenote.utils.other.SingleLiveEvent

class NotesListViewModel(private val repository: NoteRepository) : ViewModel() {
    val allNotes: LiveData<List<Note>>
        get() = repository.getNotes().asLiveData()

    val onOpenNoteSuccessfully = SingleLiveEvent<Int>()
    val onDeleteNoteSuccessfully = SingleLiveEvent<Unit>()

    fun onAttemptRemoveNote(note: Note) {
        viewModelScope.launch {
            repository.deleteNotes(note)
            onDeleteNoteSuccessfully.call()
        }
    }

    fun onAttemptOpenNote(position: Int) {
        onOpenNoteSuccessfully.value = position
    }
}