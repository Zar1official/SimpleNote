package zar1official.simplenote.ui.screens.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import zar1official.simplenote.domain.models.Note
import zar1official.simplenote.domain.usecases.GetAllNotesUseCase
import zar1official.simplenote.domain.usecases.RemoveNoteUseCase
import zar1official.simplenote.utils.other.SingleLiveEvent

class NotesListViewModel(
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val removeNoteUseCase: RemoveNoteUseCase
) : ViewModel() {
    val allNotes: LiveData<List<Note>>
        get() = getAllNotesUseCase()

    val onOpenNoteSuccessfully = SingleLiveEvent<Int>()
    val onDeleteNoteSuccessfully = SingleLiveEvent<Unit>()

    fun onAttemptRemoveNote(note: Note) {
        viewModelScope.launch {
            removeNoteUseCase(note)
            onDeleteNoteSuccessfully.call()
        }
    }

    fun onAttemptOpenNote(position: Int) {
        onOpenNoteSuccessfully.value = position
    }
}