package zar1official.simplenote.ui.screens.creating.dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import zar1official.simplenote.model.repositories.base.NoteRepository

class ConfirmCreatingViewModelFactory(private val repository: NoteRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        ConfirmCreatingViewModel(repository) as T
}