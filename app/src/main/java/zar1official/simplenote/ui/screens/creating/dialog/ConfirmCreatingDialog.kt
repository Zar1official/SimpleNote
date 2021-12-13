package zar1official.simplenote.ui.screens.creating.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import zar1official.simplenote.R
import zar1official.simplenote.application.App
import zar1official.simplenote.model.models.Note
import zar1official.simplenote.ui.screens.creating.dialog.base.ConfirmCreatingPresenter
import zar1official.simplenote.ui.screens.creating.dialog.base.ConfirmCreatingView
import zar1official.simplenote.utils.other.showSnackBar

class ConfirmCreatingDialog : DialogFragment(), ConfirmCreatingView {
    private lateinit var presenter: ConfirmCreatingPresenter
    private var note: Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            note = it?.getParcelable(DATA_PARAM)
        }
        presenter = ConfirmCreatingPresenterImpl(this, App.instance.repository)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder
                .setTitle(R.string.confirm_creating_title)
                .setMessage(R.string.confirm_creating_message)
                .setPositiveButton(R.string.confirm_creating_ok) { _, _ ->
                    lifecycleScope.launch {
                        presenter.onAttemptInsertNote(note!!)
                    }
                }
                .setNegativeButton(R.string.confirm_creating_cancel) { _, _ -> presenter.onAttemptCancel() }
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
                    DATA_PARAM to note
                )
            }
    }

    override fun onInsertSuccessfully() {
        requireParentFragment().requireView().showSnackBar(R.string.successful_save)
    }

    override fun onInsertCancel() {
    }
}