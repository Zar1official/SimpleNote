package zar1official.simplenote.ui.screens.creating.dialog

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import zar1official.simplenote.domain.Note
import zar1official.simplenote.domain.NoteRepository
import zar1official.simplenote.utils.other.SingleLiveEvent

class ConfirmCreatingViewModel(
    private val repository: NoteRepository
) : ViewModel() {

    val onInsertSuccessfully = SingleLiveEvent<Note>()

    private fun insertNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            delay(3000)
            when (repository.findNotes(note)) {
                null -> repository.saveNotes(note)
                else -> repository.updateNotes(note)
            }
        }
    }

    fun onAttemptInsertNote(note: Note) {
        insertNote(note)
        onInsertSuccessfully.value = note
    }


    fun onAttemptCancel() {}

    override fun onCleared() {
        super.onCleared()
        Log.d("model", "cleared")
    }
}