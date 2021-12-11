package zar1official.simplenote.ui.screens.creating.dialog.base

import zar1official.simplenote.model.models.Note

interface ConfirmCreatingPresenter {
    suspend fun onAttemptInsertNote(note: Note)
    fun onAttemptCancel()
}