package zar1official.simplenote.ui.screens.about.webview

import androidx.lifecycle.ViewModel
import zar1official.simplenote.utils.other.SingleLiveEvent

class WebViewFragmentViewModel : ViewModel() {
    val onAttemptGoBackSuccessfully = SingleLiveEvent<Unit>()
    val onGoBackFragment = SingleLiveEvent<Unit>()
    val onPageLoadedSuccessfully = SingleLiveEvent<Unit>()

    fun onAttemptGoBack(canGoBack: Boolean) {
        if (canGoBack) {
            onAttemptGoBackSuccessfully.call()
        } else {
            onGoBackFragment.call()
        }
    }

    fun onPageLoaded() {
        onPageLoadedSuccessfully.call()
    }
}