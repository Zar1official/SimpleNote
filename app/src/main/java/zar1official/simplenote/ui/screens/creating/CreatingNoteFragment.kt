package zar1official.simplenote.ui.screens.creating

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import zar1official.simplenote.R
import zar1official.simplenote.databinding.FragmentCreatingNoteBinding
import zar1official.simplenote.domain.Note
import zar1official.simplenote.ui.screens.creating.dialog.ConfirmCreatingDialog
import zar1official.simplenote.ui.screens.creating.dialog.ConfirmCreatingViewModel
import zar1official.simplenote.utils.other.showSnackBar

class CreatingNoteFragment : Fragment() {
    private val creatingViewModel: CreatingNoteViewModel by viewModel {
        parametersOf(
            arguments?.getParcelable(DATA_PARAM) ?: Note()
        )
    }
    private val confirmCreatingViewModel: ConfirmCreatingViewModel by viewModel()
    private var _binding: FragmentCreatingNoteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreatingNoteBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@CreatingNoteFragment
            viewmodel = creatingViewModel
        }
        setToolbarMenuItemListener()
        return binding.root
    }

    companion object {
        private const val DATA_PARAM = "note_info"

        @JvmStatic
        fun newInstance(note: Note): CreatingNoteFragment =
            CreatingNoteFragment().apply {
                arguments = bundleOf(
                    DATA_PARAM to note
                )
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeViewModel()
    }

    private fun subscribeViewModel() {
        creatingViewModel.onSuccessfulAttemptSave.observe(this) { data ->
            showConfirmDialog(data)
        }

        creatingViewModel.onFailAttemptSave.observe(this) {
            view?.showSnackBar(R.string.saved_empty_content)
        }

        creatingViewModel.onSuccessfulAttemptShare.observe(this) { note ->
            shareNote(note)
        }

        creatingViewModel.onFailAttemptShare.observe(this) {
            view?.showSnackBar(R.string.share_failed)
        }

        creatingViewModel.onFailAttemptDownload.observe(this) {
            view?.showSnackBar(R.string.download_failed)
        }
    }

    private fun shareNote(note: Note) {
        startActivity(Intent(Intent.ACTION_SEND).apply {
            type = "plain/text"
            putExtra(Intent.EXTRA_TEXT, "${note.title}\n${note.text}")
        })
    }

    private fun showConfirmDialog(note: Note) {
        ConfirmCreatingDialog.newInstance(note)
            .show(childFragmentManager, ConfirmCreatingDialog.TAG)
    }

    private fun setToolbarMenuItemListener() {
        binding.creatingNoteToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.share -> creatingViewModel.onAttemptShareNote()
                R.id.save -> creatingViewModel.onAttemptSaveNote()
                R.id.download -> creatingViewModel.onAttemptDownloadNote()
            }
            true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}