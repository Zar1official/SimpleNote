package zar1official.simplenote.model.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import zar1official.simplenote.model.models.base.DomainModel

@Parcelize
data class Note(var title: String = "", var text: String = "", var date: Long = 0) : DomainModel,
    Parcelable {
    override fun isEmpty(): Boolean = title.isEmpty() && text.isEmpty()
}