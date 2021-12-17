package zar1official.simplenote.ui.main.base

import android.os.Bundle

interface MainPresenter {
    fun onAttemptOpenNewNote()
    fun onAttemptOpenNotes()
    fun onAttemptOpenAbout()
    fun onAttemptSetHomeFragment(savedState: Bundle?)
}