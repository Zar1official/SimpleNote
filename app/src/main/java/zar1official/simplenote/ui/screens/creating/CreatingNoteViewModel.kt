package zar1official.simplenote.ui.screens.creating

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import zar1official.simplenote.domain.Note
import zar1official.simplenote.domain.NoteRepository
import zar1official.simplenote.utils.other.SingleLiveEvent
import java.util.*

class CreatingNoteViewModel(
    private val repository: NoteRepository,
    currentNote: Note = Note()
) : ViewModel() {
    val noteTitle = MutableLiveData<String>()
    val noteText = MutableLiveData<String>()
    val noteDate: Long
        get() = Calendar.getInstance().timeInMillis
    val noteID: Long = currentNote.id

    init {
        saveFields(currentNote)
    }

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
        noteDate,
        noteID
    )

    fun onAttemptShareNote() {
        val note = createNote()
        if (!note.isEmpty()) {
            onSuccessfulAttemptShare.value = note
        } else {
            onFailAttemptShare.call()
        }
    }

    private fun saveFields(note: Note) {
        noteTitle.value = note.title
        noteText.value = note.text
    }

    fun onAttemptDownloadNote() {
        viewModelScope.launch {
            runCatching {
                withContext(Dispatchers.IO) {
                    repository.loadNote()
                }
            }.onSuccess {
                saveFields(it)
            }.onFailure {
                onFailAttemptDownload.call()
            }

        }
    }
}