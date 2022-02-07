package zar1official.simplenote.ui.screens.about.cords

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import zar1official.simplenote.utils.other.SingleLiveEvent

class CordsViewModel : ViewModel() {
    val onAttemptSubscribeLocationSuccessfully = SingleLiveEvent<Unit>()
    val onAttemptShowCordsFailed = SingleLiveEvent<Unit>()
    val onAttemptGoBackSuccessfully = SingleLiveEvent<Unit>()

    val location: LiveData<Location> get() = _location
    private val _location = MutableLiveData<Location>()

    fun onAttemptShowCords(cords: Location?) {
        if (cords == null) {
            onAttemptShowCordsFailed.call()
        } else {
            _location.value = cords
        }
    }

    fun onAttemptSubscribeLocation(permissions: Collection<Boolean>) {
        if (permissions.all { it }) {
            onAttemptSubscribeLocationSuccessfully.call()
        } else {
            onAttemptShowCordsFailed.call()
        }
    }

    fun onAttemptGoBack() {
        onAttemptGoBackSuccessfully.call()
    }
}