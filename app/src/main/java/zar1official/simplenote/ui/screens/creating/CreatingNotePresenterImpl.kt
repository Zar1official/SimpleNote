package zar1official.simplenote.ui.screens.creating

import zar1official.simplenote.model.models.Note
import zar1official.simplenote.ui.screens.creating.base.CreatingNotePresenter
import zar1official.simplenote.ui.screens.creating.base.CreatingNoteView
import java.util.*

class CreatingNotePresenterImpl(
    val view: CreatingNoteView,
    private val note: Note
) :
    CreatingNotePresenter {

    override fun onAttemptSaveNote(title: String, text: String) {
        updateModel(title, text)
        if (!note.isEmpty()) {
            view.saveSuccess(note)
        } else {
            view.saveEmptyContent()
        }
    }

    override fun onAttemptShareNote(title: String, text: String) {
        updateModel(title, text)
        if (!note.isEmpty()) {
            view.shareNote(note.title, note.text)
        } else {
            view.shareFailed()
        }
    }

    private fun updateModel(title: String, text: String) {
        note.title = title
        note.text = text
        note.date = Calendar.getInstance().timeInMillis
    }

}