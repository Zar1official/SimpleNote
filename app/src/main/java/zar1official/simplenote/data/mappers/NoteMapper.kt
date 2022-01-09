package zar1official.simplenote.data.mappers

import zar1official.simplenote.data.database.entities.NoteItem
import zar1official.simplenote.data.mappers.base.EntityMapper
import zar1official.simplenote.domain.models.Note

class NoteMapper : EntityMapper<NoteItem, Note> {
    override fun mapFromEntity(entity: NoteItem): Note =
        with(entity) {
            Note(
                title = title,
                text = text,
                date = date,
                audioUri = audioUri,
                id = id
            )
        }

    override fun mapToEntity(domainModel: Note): NoteItem =
        with(domainModel) {
            NoteItem(
                title = title,
                text = text,
                date = date,
                audioUri = audioUri,
                id = id
            )
        }
}