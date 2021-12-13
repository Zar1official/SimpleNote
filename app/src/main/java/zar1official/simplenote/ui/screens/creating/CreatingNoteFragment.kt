package zar1official.simplenote.ui.screens.creating

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import zar1official.simplenote.R
import zar1official.simplenote.databinding.FragmentCreatingNoteBinding
import zar1official.simplenote.model.models.Note
import zar1official.simplenote.ui.screens.creating.base.CreatingNotePresenter
import zar1official.simplenote.ui.screens.creating.base.CreatingNoteView
import zar1official.simplenote.ui.screens.creating.dialog.ConfirmCreatingDialog
import zar1official.simplenote.utils.other.showSnackBar

class CreatingNoteFragment : Fragment(), CreatingNoteView {

    private lateinit var presenter: CreatingNotePresenter
    private var _binding: FragmentCreatingNoteBinding? = null
    private val binding get() = _binding!!
    private val noteTitle get() = binding.textInput.text.toString()
    private val noteText get() = binding.textInput.text.toString()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreatingNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = CreatingNotePresenterImpl(this, Note())
    }

    companion object {
        @JvmStatic
        fun newInstance() = CreatingNoteFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun saveSuccess(note: Note) {
        ConfirmCreatingDialog.newInstance(note)
            .show(childFragmentManager, ConfirmCreatingDialog.TAG)
    }

    override fun saveFailed() {
        view?.showSnackBar(R.string.save_failed)
    }

    override fun saveEmptyContent() {
        view?.showSnackBar(R.string.saved_empty_content)
    }

    override fun shareNote(title: String, text: String) {
        startActivity(Intent(Intent.ACTION_SEND).apply {
            type = "plain/text"
            putExtra(Intent.EXTRA_TEXT, "$title\n$text")
        })
    }

    override fun shareFailed() {
        view?.showSnackBar(R.string.share_failed)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share -> {
                lifecycleScope.launch {
                    presenter.onAttemptShareNote(
                        noteTitle,
                        noteText
                    )
                }
            }
            R.id.save -> {
                presenter.onAttemptSaveNote(
                    noteTitle,
                    noteText
                )
            }
        }
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}