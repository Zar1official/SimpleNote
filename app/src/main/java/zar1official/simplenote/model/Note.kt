package zar1official.simplenote.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Note(var title: String = "", var text: String = "", var date: Long = 0) : NoteModel,
    Parcelable {
    override fun isEmpty(): Boolean = title.isEmpty() && text.isEmpty()
}