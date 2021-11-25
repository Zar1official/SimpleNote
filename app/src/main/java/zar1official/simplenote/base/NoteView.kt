package zar1official.simplenote.base

interface NoteView {
    fun saveSuccess()
    fun saveFailed()
    fun saveEmptyContent()
    fun shareNote(title: String, text: String)
    fun shareFailed()
    fun openAbout()
}