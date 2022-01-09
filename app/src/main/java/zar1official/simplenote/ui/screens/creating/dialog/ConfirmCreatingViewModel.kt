package zar1official.simplenote.ui.screens.creating.dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import zar1official.simplenote.domain.models.Note
import zar1official.simplenote.domain.usecases.FindNoteByIdUseCase
import zar1official.simplenote.domain.usecases.SaveNoteUseCase
import zar1official.simplenote.domain.usecases.UpdateNoteUseCase
import zar1official.simplenote.utils.other.SingleLiveEvent

class ConfirmCreatingViewModel(
    private val findNoteByIdUseCase: FindNoteByIdUseCase,
    private val saveNoteUseCase: SaveNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase
) : ViewModel() {

    val onInsertSuccessfully = SingleLiveEvent<Note>()

    private fun insertNote(note: Note) {
        viewModelScope.launch {
            when (findNoteByIdUseCase(note)) {
                null -> saveNoteUseCase(note)
                else -> updateNoteUseCase(note)
            }
            onInsertSuccessfully.value = note
        }
    }

    fun onAttemptInsertNote(note: Note) {
        insertNote(note)
    }

    fun onAttemptCancel() {}
}