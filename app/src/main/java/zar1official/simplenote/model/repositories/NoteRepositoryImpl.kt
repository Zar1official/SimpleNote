package zar1official.simplenote.model.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import zar1official.simplenote.model.database.NoteDao
import zar1official.simplenote.model.models.Note
import zar1official.simplenote.model.network.service.NoteService
import zar1official.simplenote.model.repositories.base.NoteRepository
import zar1official.simplenote.utils.mappers.NetworkNoteMapper
import zar1official.simplenote.utils.mappers.NoteMapper

class NoteRepositoryImpl(
    private val noteDao: NoteDao,
    private val noteService: NoteService,
    private val mapper: NoteMapper,
    private val networkMapper: NetworkNoteMapper
) :
    NoteRepository {
    override suspend fun loadNote(): Note =
        networkMapper.mapFromEntity(noteService.getNote())


    override suspend fun saveNotes(noteModel: Note) {
        noteDao.insertNote(mapper.mapToEntity(noteModel))
    }

    override suspend fun updateNotes(noteModel: Note) {
        noteDao.updateNote(mapper.mapToEntity(noteModel))
    }

    override suspend fun deleteNotes(noteModel: Note) {
        noteDao.deleteNote(mapper.mapToEntity(noteModel))
    }

    override fun getNotes(): Flow<List<Note>> {
        val data = noteDao.getAllNotes()
        return data.map { mapper.mapFromEntityList(it) }
    }

}