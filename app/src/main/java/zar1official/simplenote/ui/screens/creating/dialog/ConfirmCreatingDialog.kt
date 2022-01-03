package zar1official.simplenote.ui.screens.creating.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import zar1official.simplenote.R
import zar1official.simplenote.domain.Note
import zar1official.simplenote.utils.other.showSnackBar

class ConfirmCreatingDialog : DialogFragment() {
    private val viewModel: ConfirmCreatingViewModel by sharedViewModel()
    private var note: Note = Note()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getArgs()
        subscribeViewModel()
    }

    private fun subscribeViewModel() {
        viewModel.onInsertSuccessfully.observe(this) { data ->
            requireParentFragment().requireView().showSnackBar(R.string.successful_save)
            activity?.sendBroadcast(Intent().apply {
                action = ACTION
                putExtra(TITLE_PARAM, data.title)
                putExtra(TEXT_PARAM, data.text)
                putExtra(DATE_PARAM, data.date)
            })
        }
    }

    private fun getArgs() {
        arguments?.let {
            note = it.getParcelable(DATA_PARAM) ?: note
        }
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
        private const val TITLE_PARAM = "title"
        private const val TEXT_PARAM = "text"
        private const val DATE_PARAM = "date"
        private const val ACTION = "com.zar1official.simplenote.action_note_saved"
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