package zar1official.simplenote.ui.screens.about

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AboutViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        AboutViewModel() as T
}