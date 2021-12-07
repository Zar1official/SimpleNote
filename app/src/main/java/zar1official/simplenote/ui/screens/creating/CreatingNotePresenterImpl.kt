package zar1official.simplenote.ui.screens.creating

import zar1official.simplenote.model.Note
import zar1official.simplenote.ui.screens.creating.base.CreatingNotePresenter
import zar1official.simplenote.ui.screens.creating.base.CreatingNoteView
import java.util.*

class CreatingNotePresenterImpl(val view: CreatingNoteView, val note: Note) :
    CreatingNotePresenter {

    private fun updateTitle(title: String) {
        note.title = title
    }

    private fun updateText(text: String) {
        note.text = text
    }

    private fun updateDate() {
        note.date = Calendar.getInstance().timeInMillis
    }

    override fun onAttemptSaveNote(title: String, text: String) {
        if (title.isNotEmpty() && text.isNotEmpty()) {
            updateTitle(title)
            updateText(text)
            updateDate()
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

}