package zar1official.simplenote.ui.screens.notes.base

import kotlinx.coroutines.flow.Flow
import zar1official.simplenote.model.models.Note

interface NoteListView {
    fun onLoadedDataSuccessfully(data: Flow<List<Note>>)
    fun onLoadingDataFailed()
    fun openNote(position: Int, notesList: ArrayList<Note>)
}