package zar1official.simplenote.ui.screens.notes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import zar1official.simplenote.model.models.Note
import zar1official.simplenote.model.repositories.base.NoteRepository

class NotesListViewModel(private val repository: NoteRepository) : ViewModel() {
    val allNotes = MutableLiveData<List<Note>?>()

    fun loadData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.getNotes()
            }.collect {
                allNotes.value = it
            }
        }
    }
}