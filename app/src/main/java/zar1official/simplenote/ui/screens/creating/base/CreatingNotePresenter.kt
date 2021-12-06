package zar1official.simplenote.ui.screens.creating.base

interface CreatingNotePresenter {
    fun onAttemptSaveNote(title: String, text: String)
    fun onAttemptShareNote()
}