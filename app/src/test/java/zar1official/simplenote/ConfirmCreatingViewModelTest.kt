package zar1official.simplenote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import zar1official.simplenote.domain.models.Note
import zar1official.simplenote.domain.repositories.NoteRepository
import zar1official.simplenote.domain.usecases.FindNoteByIdUseCase
import zar1official.simplenote.domain.usecases.SaveNoteUseCase
import zar1official.simplenote.domain.usecases.UpdateNoteUseCase
import zar1official.simplenote.ui.screens.creating.dialog.ConfirmCreatingViewModel

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class ConfirmCreatingViewModelTest {

    private lateinit var viewModel: ConfirmCreatingViewModel
    private lateinit var repository: NoteRepository
    private lateinit var findNoteByIdUseCase: FindNoteByIdUseCase
    private lateinit var saveNoteByIdUseCase: SaveNoteUseCase
    private lateinit var updateNoteUseCase: UpdateNoteUseCase

    private val dispatcher = TestCoroutineDispatcher()

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        repository = mock()
        findNoteByIdUseCase = FindNoteByIdUseCase(repository)
        saveNoteByIdUseCase = SaveNoteUseCase(repository)
        updateNoteUseCase = UpdateNoteUseCase(repository)
        viewModel =
            ConfirmCreatingViewModel(findNoteByIdUseCase, saveNoteByIdUseCase, updateNoteUseCase)
    }

    @Test
    fun saveNoteTest() = dispatcher.runBlockingTest {
        val note = Note("title", "text")
        var successfulAttempt = false

        viewModel.onAttemptInsertNote(note)
        verify(repository).findNotes(note)
        verify(repository).saveNotes(note)
        viewModel.onInsertSuccessfully.observeForever {
            successfulAttempt = true
        }
        assertTrue(successfulAttempt)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        dispatcher.cleanupTestCoroutines()
    }
}