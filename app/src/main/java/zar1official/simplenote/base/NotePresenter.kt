package zar1official.simplenote.base

interface NotePresenter {
    fun onAttemptSaveNote(title: String, text: String)
    fun onAttemptShareNote()
    fun onAttemptOpenAbout()
}