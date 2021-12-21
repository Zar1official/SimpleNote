package zar1official.simplenote.ui.screens.notes.info.item

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NoteInfoViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        NoteInfoViewModel() as T
}