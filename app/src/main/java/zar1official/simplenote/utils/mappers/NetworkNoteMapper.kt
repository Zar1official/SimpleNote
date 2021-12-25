package zar1official.simplenote.utils.mappers

import zar1official.simplenote.model.models.Note
import zar1official.simplenote.model.network.entities.NetworkNote
import zar1official.simplenote.utils.mappers.base.EntityMapper

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