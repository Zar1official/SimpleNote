package zar1official.simplenote.ui.screens.creating

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import zar1official.simplenote.R
import zar1official.simplenote.databinding.FragmentCreatingNoteBinding
import zar1official.simplenote.model.models.Note
import zar1official.simplenote.ui.screens.creating.dialog.ConfirmCreatingDialog
import zar1official.simplenote.utils.other.showSnackBar

class CreatingNoteFragment : Fragment() {

    private lateinit var viewModelFactory: CreatingNoteViewModelFactory
    private val viewModel: CreatingNoteViewModel by viewModels { viewModelFactory }
    private var _binding: FragmentCreatingNoteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreatingNoteBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@CreatingNoteFragment
            viewmodel = viewModel
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = CreatingNoteFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelFactory = CreatingNoteViewModelFactory()
        subscribeViewModel()
        setHasOptionsMenu(true)
    }

    private fun subscribeViewModel() {
        viewModel.onSuccessfulAttemptSave.observe(this) { note ->
            showConfirmDialog(note)
        }

        viewModel.onFailAttemptSave.observe(this) {
            view?.showSnackBar(R.string.saved_empty_content)
        }

        viewModel.onSuccessfulAttemptShare.observe(this) { note ->
            shareNote(note)
        }

        viewModel.onFailAttemptShare.observe(this) {
            view?.showSnackBar(R.string.share_failed)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share -> {
                viewModel.onAttemptShareNote()
            }
            R.id.save -> {
                viewModel.onAttemptSaveNote()
            }
        }
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}