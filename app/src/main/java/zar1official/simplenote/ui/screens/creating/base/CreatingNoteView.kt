package zar1official.simplenote.ui.screens.creating.base

interface CreatingNoteView {
    fun saveSuccess()
    fun saveFailed()
    fun saveEmptyContent()
    fun shareNote(title: String, text: String)
    fun shareFailed()
}

