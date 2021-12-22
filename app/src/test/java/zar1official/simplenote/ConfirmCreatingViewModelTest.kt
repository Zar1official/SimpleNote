package zar1official.simplenote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.kotlin.mock
import zar1official.simplenote.model.models.Note
import zar1official.simplenote.model.repositories.base.NoteRepository
import zar1official.simplenote.ui.screens.creating.dialog.ConfirmCreatingViewModel

@RunWith(JUnit4::class)
class ConfirmCreatingViewModelTest {

    private lateinit var viewModel: ConfirmCreatingViewModel
    private lateinit var repository: NoteRepository

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        repository = mock()
        viewModel = ConfirmCreatingViewModel(repository)
    }

    @Test
    fun insertNoteTest() = runBlocking {
        val note = Note("title", "text")
        var successfulAttempt = false

        viewModel.onAttemptInsertNote(note)
        viewModel.onInsertSuccessfully.observeForever {
            successfulAttempt = true
        }

        assertTrue(successfulAttempt)
    }
}