package zar1official.simplenote.domain.usecases

import androidx.lifecycle.asLiveData
import zar1official.simplenote.domain.repositories.NoteRepository

class GetAllNotesUseCase(private val repository: NoteRepository) {
    operator fun invoke() = repository.getNotes().asLiveData()
}