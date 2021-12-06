package zar1official.simplenote.model.repositories

import zar1official.simplenote.model.Note
import zar1official.simplenote.model.repositories.base.NoteRepository

object NoteRepositoryImpl : NoteRepository {

    val storage = listOf(
        Note(
            "longtexttest",
            "longtexttestlongtexttestlongtexttestlongtexttestlongtexttestlongtexttestlongtexttestlongtexttestlongtexttestlongtexttest",
            1638727250
        ),
        Note("longheadertestlongheadertestlongheadertestlongheadertest", "default", 1638727261),
        Note("default", "default"),
        Note("default", "default"),
        Note("default", "default"),
        Note("default", "default"),
        Note("default", "default"),
        Note("default", "default")
    )

    override fun saveNote() {
        TODO("Not yet implemented")
    }

    override fun getNotes(): List<Note>? = storage
}