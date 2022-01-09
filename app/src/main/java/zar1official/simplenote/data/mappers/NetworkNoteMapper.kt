package zar1official.simplenote.data.mappers

import zar1official.simplenote.data.mappers.base.EntityMapper
import zar1official.simplenote.data.network.entities.NetworkNote
import zar1official.simplenote.domain.models.Note

class NetworkNoteMapper : EntityMapper<NetworkNote, Note> {
    override fun mapFromEntity(entity: NetworkNote): Note =
        with(entity) {
            Note(
                title = title,
                text = text
            )
        }

    override fun mapToEntity(domainModel: Note): NetworkNote =
        with(domainModel) {
            NetworkNote(
                title = title,
                text = text
            )
        }
}