package zar1official.simplenote.ui.screens.about

import androidx.lifecycle.ViewModel
import zar1official.simplenote.utils.other.SingleLiveEvent

class AboutViewModel : ViewModel() {
    val onAttemptOpenWebViewSuccessfully = SingleLiveEvent<Unit>()
    fun onAttemptOpenWebView() {
        onAttemptOpenWebViewSuccessfully.call()
    }
}