package zar1official.simplenote.domain.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import zar1official.simplenote.domain.models.Note
import zar1official.simplenote.domain.repositories.NoteRepository

class LoadNoteUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(): Note? =
        runCatching {
            withContext(Dispatchers.IO) {
                repository.loadNote()
            }
        }.getOrNull()
}