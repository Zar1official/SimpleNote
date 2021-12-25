package zar1official.simplenote.utils.mappers

import zar1official.simplenote.model.database.entities.NoteItem
import zar1official.simplenote.model.models.Note
import zar1official.simplenote.utils.mappers.base.EntityMapper

class NoteMapper : EntityMapper<NoteItem, Note> {
    override fun mapFromEntity(entity: NoteItem): Note =
        with(entity) {
            Note(
                title = title,
                text = text,
                date = date
            )
        }

    override fun mapToEntity(domainModel: Note): NoteItem =
        with(domainModel) {
            NoteItem(
                title = title,
                text = text,
                date = date
            )
        }
}