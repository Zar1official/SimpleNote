package zar1official.simplenote.ui.main

import android.os.Bundle
import androidx.lifecycle.ViewModel
import zar1official.simplenote.utils.other.SingleLiveEvent

class MainViewModel : ViewModel() {
    val onOpenNotes = SingleLiveEvent<Unit>()
    val onOpenNewNote = SingleLiveEvent<Unit>()
    val onOpenAbout = SingleLiveEvent<Unit>()
    val onSetHomeFragment = SingleLiveEvent<Unit>()

    fun onAttemptOpenNewNote() {
        onOpenNewNote.call()
    }

    fun onAttemptOpenNotes() {
        onOpenNotes.call()
    }

    fun onAttemptOpenAbout() {
        onOpenAbout.call()
    }

    fun onAttemptSetHome(savedState: Bundle?) {
        if (savedState == null) {
            onSetHomeFragment.call()
        }
    }
}