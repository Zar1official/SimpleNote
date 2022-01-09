package zar1official.simplenote.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import zar1official.simplenote.data.database.NoteDao
import zar1official.simplenote.data.mappers.NetworkNoteMapper
import zar1official.simplenote.data.mappers.NoteMapper
import zar1official.simplenote.data.network.service.NoteService
import zar1official.simplenote.domain.models.Note
import zar1official.simplenote.domain.repositories.NoteRepository

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

    override suspend fun findNotes(noteModel: Note): Note? =
        noteDao.findNoteById(noteModel.id)?.let { mapper.mapFromEntity(it) }

    override fun getNotes(): Flow<List<Note>> =
        noteDao.getAllNotes().map { mapper.mapFromEntityList(it) }
}