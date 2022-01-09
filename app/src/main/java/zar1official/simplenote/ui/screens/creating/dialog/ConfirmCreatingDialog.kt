package zar1official.simplenote.ui.screens.creating.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import zar1official.simplenote.R
import zar1official.simplenote.domain.models.Note
import zar1official.simplenote.ui.base.view.Subscriber

class ConfirmCreatingDialog : DialogFragment(), Subscriber {
    private val viewModel: ConfirmCreatingViewModel by sharedViewModel()
    private var note: Note = Note()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getArgs()
        subscribeViewModel()
    }

    override fun subscribeViewModel() {
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