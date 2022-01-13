package zar1official.simplenote.ui.screens.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import zar1official.simplenote.domain.models.Note
import zar1official.simplenote.domain.usecases.GetAllNotesLiveDataUseCase
import zar1official.simplenote.domain.usecases.RemoveNoteUseCase
import zar1official.simplenote.utils.other.SingleLiveEvent

class NotesListViewModel(
    private val getAllNotesUseCase: GetAllNotesLiveDataUseCase,
    private val removeNoteUseCase: RemoveNoteUseCase
) : ViewModel() {
    val allNotes: LiveData<List<Note>>
        get() = getAllNotesUseCase()

    private val _noteFilter = MutableLiveData<String>()
    val noteFilter: LiveData<String>
        get() = _noteFilter

    private val _currentNoteList = MutableLiveData<List<Note>>()
    val currentNoteList: LiveData<List<Note>>
        get() = _currentNoteList

    private val _currentFilteredNoteList = MutableLiveData<List<Note>>()
    val currentFilteredNoteList: LiveData<List<Note>>
        get() = _currentFilteredNoteList

    val onOpenNoteSuccessfully = SingleLiveEvent<Pair<Int, String?>>()
    val onDeleteNoteSuccessfully = SingleLiveEvent<Unit>()

    fun onAttemptRemoveNote(note: Note) {
        viewModelScope.launch {
            removeNoteUseCase(note)
            onDeleteNoteSuccessfully.call()
        }
    }

    fun onAttemptOpenNote(position: Int) {
        onOpenNoteSuccessfully.value = Pair(position, noteFilter.value)
    }


    fun onAttemptUpdateNotes(notes: List<Note>) {
        _currentNoteList.value = notes
    }

    fun onAttemptUpdateFilteredList() {
        _currentFilteredNoteList.value = when (noteFilter.value) {
            null -> _currentNoteList.value
            else -> _currentNoteList.value?.filter { note ->
                note.title.contains(noteFilter.value!!) || note.text.contains(
                    noteFilter.value!!
                )
            }
        }
    }

    fun onAttemptUpdateFilter(filter: String?) {
        _noteFilter.value = filter.orEmpty()
    }
}