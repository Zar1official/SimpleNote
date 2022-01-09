package zar1official.simplenote.domain.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import zar1official.simplenote.domain.models.base.DomainModel

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