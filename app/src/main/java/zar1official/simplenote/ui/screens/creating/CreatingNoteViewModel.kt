package zar1official.simplenote.ui.screens.creating

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import zar1official.simplenote.model.models.Note
import zar1official.simplenote.utils.other.SingleLiveEvent
import java.util.*

class CreatingNoteViewModel : ViewModel() {
    val noteTitle = MutableLiveData<String>()
    val noteText = MutableLiveData<String>()
    val noteDate: Long
        get() = Calendar.getInstance().timeInMillis

    val onSuccessfulAttemptSave = SingleLiveEvent<Note>()
    val onFailAttemptSave = SingleLiveEvent<Unit>()
    val onSuccessfulAttemptShare = SingleLiveEvent<Note>()
    val onFailAttemptShare = SingleLiveEvent<Unit>()

    fun onAttemptSaveNote() {
        val note = createNote()
        if (!note.isEmpty()) {
            onSuccessfulAttemptSave.value = note
        } else {
            onFailAttemptSave.call()
        }
    }

    private fun createNote() = Note(
        noteTitle.value.orEmpty(),
        noteText.value.orEmpty(),
        noteDate
    )

    fun onAttemptShareNote() {
        val note = createNote()
        if (!note.isEmpty()) {
            onSuccessfulAttemptShare.value = note
        } else {
            onFailAttemptShare.call()
        }
    }
}