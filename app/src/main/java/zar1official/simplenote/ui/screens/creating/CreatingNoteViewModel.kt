package zar1official.simplenote.ui.screens.creating

import android.net.Uri
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
    currentNote: Note
) : ViewModel() {
    val noteTitle = MutableLiveData<String>()
    val noteText = MutableLiveData<String>()
    val noteDate: Long
        get() = Calendar.getInstance().timeInMillis
    val noteID: Long = currentNote.id
    var noteAudio = MutableLiveData<Uri?>()

    init {
        saveFields(currentNote)
    }

    val onSuccessfulAttemptSave = SingleLiveEvent<Note>()
    val onFailAttemptSave = SingleLiveEvent<Unit>()
    val onSuccessfulAttemptShare = SingleLiveEvent<Note>()
    val onFailAttemptShare = SingleLiveEvent<Unit>()
    val onFailAttemptDownload = SingleLiveEvent<Unit>()
    val onSuccessfulAttemptPlayMusic = SingleLiveEvent<Uri>()
    val onUnsuccessfulAttemptPlayMusic = SingleLiveEvent<Unit>()
    val onSuccessfulAttemptUploadMusic = SingleLiveEvent<Unit>()
    val onSuccessfulAttemptDeleteMusic = SingleLiveEvent<Unit>()
    val onFailAttemptDeleteMusic = SingleLiveEvent<Unit>()

    val playerState = MutableLiveData<Boolean>().apply { value = false }

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
        noteID,
        noteAudio.value
    )

    fun onAttemptShareNote() {
        val note = createNote()
        if (!note.isEmpty()) {
            onSuccessfulAttemptShare.value = note
        } else {
            onFailAttemptShare.call()
        }
    }

    fun saveFields(note: Note) {
        noteTitle.value = note.title
        noteText.value = note.text
        noteAudio.value = note.audioUri
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

    fun onAttemptPlayMusic() {
        when (noteAudio.value) {
            null -> onUnsuccessfulAttemptPlayMusic.call()
            else -> onSuccessfulAttemptPlayMusic.value = noteAudio.value
        }
    }

    fun onAttemptSaveAudioUri(uri: Uri?) {
        if (uri != null) {
            noteAudio.value = uri
        }
    }

    fun onAttemptUploadMusic() {
        onSuccessfulAttemptUploadMusic.call()
    }

    fun onAttemptChangePlayerState() {
        playerState.value = !playerState.value!!
    }

    fun onAttemptDeleteMusic() {
        when (noteAudio.value) {
            null -> {
                onFailAttemptDeleteMusic.call()
            }
            else -> {
                onSuccessfulAttemptDeleteMusic.call()
                playerState.value = false
                noteAudio.value = null
            }
        }
    }
}