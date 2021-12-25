package zar1official.simplenote.ui.screens.creating

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import zar1official.simplenote.model.models.Note
import zar1official.simplenote.model.repositories.base.NoteRepository
import zar1official.simplenote.utils.other.SingleLiveEvent
import java.util.*

class CreatingNoteViewModel(private val repository: NoteRepository) : ViewModel() {
    val noteTitle = MutableLiveData<String>()
    val noteText = MutableLiveData<String>()
    val noteDate: Long
        get() = Calendar.getInstance().timeInMillis


    val onSuccessfulAttemptSave = SingleLiveEvent<Note>()
    val onFailAttemptSave = SingleLiveEvent<Unit>()
    val onSuccessfulAttemptShare = SingleLiveEvent<Note>()
    val onFailAttemptShare = SingleLiveEvent<Unit>()
    val onFailAttemptDownload = SingleLiveEvent<Unit>()

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


    fun onAttemptDownloadNote() {
        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO) {
                    repository.loadNote()
                }
            }.onSuccess {
                noteTitle.value = it.title
                noteText.value = it.text
            }.onFailure {
                onFailAttemptDownload.call()
            }

        }
    }
}