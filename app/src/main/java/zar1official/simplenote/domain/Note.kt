package zar1official.simplenote.domain

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Note(
    var title: String = "",
    var text: String = "",
    var date: Long = 0,
    var id: Long = 0,
    var audioUri: Uri? = null
) : DomainModel,
    Parcelable {
    override fun isEmpty(): Boolean = title.isEmpty() || text.isEmpty()
}