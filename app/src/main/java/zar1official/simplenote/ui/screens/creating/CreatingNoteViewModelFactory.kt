package zar1official.simplenote.ui.screens.creating

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CreatingNoteViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        CreatingNoteViewModel() as T
}