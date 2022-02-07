package zar1official.simplenote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.kotlin.mock
import zar1official.simplenote.domain.models.Note
import zar1official.simplenote.domain.repositories.NoteRepository
import zar1official.simplenote.domain.usecases.LoadNoteUseCase
import zar1official.simplenote.ui.screens.creating.CreatingNoteViewModel

@RunWith(JUnit4::class)
class CreatingNoteViewModelTest {

    private lateinit var viewModel: CreatingNoteViewModel
    private lateinit var repository: NoteRepository
    private lateinit var loadNoteUseCase: LoadNoteUseCase

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        repository = mock()
        loadNoteUseCase = LoadNoteUseCase(repository)
        viewModel = CreatingNoteViewModel(loadNoteUseCase, Note())
    }

    @Test
    fun onAttemptSaveNoteSuccessfullyTest() {
        viewModel.saveFields(Note("title", "text"))
        var successfulAttempt = false

        viewModel.onAttemptSaveNote()
        viewModel.onSuccessfulAttemptSave.observeForever {
            successfulAttempt = true
        }

        assertTrue(successfulAttempt)
    }

    @Test
    fun onAttemptSaveNoteFailedTest() {
        val badNotes = listOf(Note(), Note(title = "title"), Note(text = "text"))
        badNotes.forEach { note ->
            var unsuccessfulAttempt = false
            viewModel.saveFields(note)
            viewModel.onAttemptSaveNote()
            viewModel.onFailAttemptSave.observeForever {
                unsuccessfulAttempt = true
            }
            assertTrue(unsuccessfulAttempt)
        }
    }

    @Test
    fun onAttemptShareNoteSuccessfullyTest() {
        viewModel.saveFields(Note("title", "text"))
        var successfulAttempt = false

        viewModel.onAttemptShareNote()
        viewModel.onSuccessfulAttemptShare.observeForever {
            successfulAttempt = true
        }

        assertTrue(successfulAttempt)
    }

    @Test
    fun onAttemptShareNoteFailedTest() {
        val badNotes = listOf(Note(), Note(title = "title"), Note(text = "text"))
        badNotes.forEach { note ->
            var unsuccessfulAttempt = false
            viewModel.saveFields(note)
            viewModel.onAttemptShareNote()
            viewModel.onFailAttemptShare.observeForever {
                unsuccessfulAttempt = true
            }
            assertTrue(unsuccessfulAttempt)
        }
    }

    @Test
    fun onAttemptPlayMusicSuccessfullyTest() {
        viewModel.saveFields(Note(audioUri = mock()))
        var successfulAttempt = false

        viewModel.onAttemptPlayMusic()
        viewModel.onSuccessfulAttemptPlayMusic.observeForever {
            successfulAttempt = true
        }
        assertTrue(successfulAttempt)
    }

    @Test
    fun onAttemptPlayMusicFailedTest() {
        viewModel.saveFields(Note(audioUri = null))
        var unsuccessfulAttempt = false

        viewModel.onAttemptPlayMusic()
        viewModel.onUnsuccessfulAttemptPlayMusic.observeForever {
            unsuccessfulAttempt = true
        }
        assertTrue(unsuccessfulAttempt)
    }

    @Test
    fun onAttemptDeleteMusicSuccessfullyTest() {
        viewModel.saveFields(Note(audioUri = mock()))
        var successfulAttempt = false

        viewModel.onAttemptDeleteMusic()
        viewModel.onSuccessfulAttemptDeleteMusic.observeForever {
            successfulAttempt = true
        }
        assertTrue(successfulAttempt)
    }

    @Test
    fun onAttemptDeleteMusicFailedTest() {
        viewModel.saveFields(Note(audioUri = null))
        var unsuccessfulAttempt = false

        viewModel.onAttemptDeleteMusic()
        viewModel.onFailAttemptDeleteMusic.observeForever {
            unsuccessfulAttempt = true
        }
        assertTrue(unsuccessfulAttempt)
    }

    @Test
    fun onAttemptUploadMusicSuccessfullyTest() {
        var successfulAttempt = false
        viewModel.onAttemptUploadMusic()
        viewModel.onSuccessfulAttemptUploadMusic.observeForever {
            successfulAttempt = true
        }
        assertTrue(successfulAttempt)
    }
}