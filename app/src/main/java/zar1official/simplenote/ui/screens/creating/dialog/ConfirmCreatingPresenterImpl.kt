package zar1official.simplenote.ui.screens.creating.dialog

import zar1official.simplenote.model.models.Note
import zar1official.simplenote.model.repositories.base.NoteRepository
import zar1official.simplenote.ui.screens.creating.dialog.base.ConfirmCreatingPresenter
import zar1official.simplenote.ui.screens.creating.dialog.base.ConfirmCreatingView

class ConfirmCreatingPresenterImpl(
    val view: ConfirmCreatingView,
    private val repository: NoteRepository
) : ConfirmCreatingPresenter {
    override suspend fun onAttemptInsertNote(note: Note) {
        repository.saveNotes(note)
        view.onInsertSuccessfully()
    }

    override fun onAttemptCancel() {
        view.onInsertCancel()
    }
}