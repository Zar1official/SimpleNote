package zar1official.simplenote.presenter

import zar1official.simplenote.base.NotePresenter
import zar1official.simplenote.base.NoteView
import zar1official.simplenote.model.Note

class MainActivityPresenter(val view: NoteView) : NotePresenter {
    val note = Note()

    private fun updateTitle(title: String) {
        note.title = title
    }

    private fun updateText(text: String) {
        note.text = text
    }

    override fun onAttemptSaveNote(title: String, text: String) {
        if (title.isNotEmpty() && text.isNotEmpty()) {
            updateTitle(title)
            updateText(text)
            view.saveSuccess()
        } else {
            view.saveEmptyContent()
        }
    }

    override fun onAttemptShareNote() {
        if (!note.isEmpty()) {
            view.shareNote(note.title, note.text)
        } else {
            view.shareFailed()
        }
    }

    override fun onAttemptOpenAbout() {
        view.openAbout()
    }
}