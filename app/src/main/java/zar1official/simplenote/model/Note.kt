package zar1official.simplenote.model

import zar1official.simplenote.base.NoteModel

class Note(var title: String = "", var text: String = "") : NoteModel {
    override fun isEmpty(): Boolean = title.isEmpty() && text.isEmpty()
}