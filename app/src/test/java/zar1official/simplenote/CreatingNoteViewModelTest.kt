package zar1official.simplenote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import zar1official.simplenote.ui.screens.creating.CreatingNoteViewModel

@RunWith(JUnit4::class)
class CreatingNoteViewModelTest {

    private lateinit var viewModel: CreatingNoteViewModel

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = CreatingNoteViewModel()
    }

    @Test
    fun onAttemptSaveNoteSuccessfully() {
        viewModel.noteTitle.value = "title"
        viewModel.noteText.value = "text"
        var successfulAttempt = false

        viewModel.onAttemptSaveNote()
        viewModel.onSuccessfulAttemptSave.observeForever {
            successfulAttempt = true
        }

        assertTrue(successfulAttempt)
    }

    @Test
    fun onAttemptSaveNoteFailed() {
        viewModel.noteTitle.value = "title"
        viewModel.noteText.value = ""
        var unsuccessfulAttempt = false

        viewModel.onAttemptSaveNote()
        viewModel.onFailAttemptSave.observeForever {
            unsuccessfulAttempt = true
        }

        assertTrue(unsuccessfulAttempt)
    }

    @Test
    fun onAttemptShareNoteSuccessfully() {
        viewModel.noteTitle.value = "title"
        viewModel.noteText.value = "text"
        var successfulAttempt = false

        viewModel.onAttemptShareNote()
        viewModel.onSuccessfulAttemptShare.observeForever {
            successfulAttempt = true
        }

        assertTrue(successfulAttempt)
    }

    @Test
    fun onAttemptShareNoteFailed() {
        viewModel.noteTitle.value = "title"
        viewModel.noteText.value = ""
        var unsuccessfulAttempt = false

        viewModel.onAttemptShareNote()
        viewModel.onFailAttemptShare.observeForever {
            unsuccessfulAttempt = true
        }

        assertTrue(unsuccessfulAttempt)
    }

}