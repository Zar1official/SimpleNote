package zar1official.simplenote.ui.screens.creating.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import zar1official.simplenote.R
import zar1official.simplenote.application.App
import zar1official.simplenote.data.mappers.NetworkNoteMapper
import zar1official.simplenote.data.mappers.NoteMapper
import zar1official.simplenote.data.network.service.NoteService
import zar1official.simplenote.data.repositories.NoteRepositoryImpl
import zar1official.simplenote.domain.Note
import zar1official.simplenote.domain.NoteRepository
import zar1official.simplenote.utils.other.showSnackBar

class ConfirmCreatingDialog : DialogFragment() {
    private lateinit var repository: NoteRepository
    private val viewModel: ConfirmCreatingViewModel by viewModels(ownerProducer = { requireParentFragment() }) {
        ConfirmCreatingViewModelFactory(
            repository
        )
    }
    private var note: Note = Note()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getArgs()
        initRepository()
        subscribeViewModel()
    }

    private fun subscribeViewModel() {
        viewModel.onInsertSuccessfully.observe(this) {
            requireParentFragment().requireView().showSnackBar(R.string.successful_save)
        }
    }

    private fun getArgs() {
        arguments?.let {
            note = it.getParcelable(DATA_PARAM) ?: note
        }
    }

    private fun initRepository() {
        val noteDao = App.instance.db.noteDao()
        val noteService = App.instance.retrofitClient.create(NoteService::class.java)
        repository = NoteRepositoryImpl(noteDao, noteService, NoteMapper(), NetworkNoteMapper())
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder
                .setTitle(R.string.confirm_creating_title)
                .setMessage(R.string.confirm_creating_message)
                .setPositiveButton(R.string.confirm_creating_ok) { _, _ ->
                    viewModel.onAttemptInsertNote(note)
                }
                .setNegativeButton(R.string.confirm_creating_cancel) { _, _ -> viewModel.onAttemptCancel() }
                .create()
        } ?: throw IllegalArgumentException()
    }

    companion object {
        private const val DATA_PARAM = "note"
        const val TAG = "ConfirmCreatingDialog"

        @JvmStatic
        fun newInstance(note: Note): ConfirmCreatingDialog =
            ConfirmCreatingDialog().apply {
                arguments = bundleOf(
                    DATA_PARAM to note,
                )
            }
    }
}