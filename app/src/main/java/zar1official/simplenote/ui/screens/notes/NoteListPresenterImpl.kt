package zar1official.simplenote.ui.screens.notes

import zar1official.simplenote.model.Note
import zar1official.simplenote.model.repositories.base.NoteRepository
import zar1official.simplenote.ui.screens.notes.base.NoteListPresenter
import zar1official.simplenote.ui.screens.notes.base.NoteListView

class NoteListPresenterImpl(val view: NoteListView, val repository: NoteRepository) :
    NoteListPresenter {

    override fun onLoadData() = when (val data = repository.getNotes()) {
        null -> view.onLoadingDataFailed()
        else -> view.onLoadedDataSuccessfully(data)
    }

    override fun onAttemptOpenNote(note: Note) {
        view.openNote(note)
    }
}