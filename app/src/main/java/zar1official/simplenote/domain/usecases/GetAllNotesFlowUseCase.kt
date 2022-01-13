package zar1official.simplenote.domain.usecases

import zar1official.simplenote.domain.repositories.NoteRepository

class GetAllNotesFlowUseCase(private val repository: NoteRepository) {
    operator fun invoke() = repository.getNotes()
}