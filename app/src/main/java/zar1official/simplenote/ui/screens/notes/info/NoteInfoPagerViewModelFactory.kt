package zar1official.simplenote.ui.screens.notes.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NoteInfoPagerViewModelFactory :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        NoteInfoPagerViewModel() as T
}