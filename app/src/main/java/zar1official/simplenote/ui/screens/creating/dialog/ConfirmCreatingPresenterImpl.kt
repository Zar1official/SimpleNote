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
        view.onInsertSuccessfully()
        repository.saveNotes(note)
    }

    override fun onAttemptCancel() {
        view.onInsertCancel()
    }
}