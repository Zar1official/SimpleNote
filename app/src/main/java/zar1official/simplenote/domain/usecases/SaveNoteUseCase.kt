package zar1official.simplenote.domain.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import zar1official.simplenote.domain.models.Note
import zar1official.simplenote.domain.repositories.NoteRepository

class SaveNoteUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(note: Note) =
        withContext(Dispatchers.IO) {
            repository.saveNotes(note)
        }
}