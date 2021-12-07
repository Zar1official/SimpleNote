package zar1official.simplenote.ui.screens.creating

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import zar1official.simplenote.R
import zar1official.simplenote.databinding.FragmentCreatingNoteBinding
import zar1official.simplenote.model.Note
import zar1official.simplenote.ui.screens.creating.base.CreatingNotePresenter
import zar1official.simplenote.ui.screens.creating.base.CreatingNoteView

class CreatingNoteFragment : Fragment(), CreatingNoteView {

    private lateinit var presenter: CreatingNotePresenter
    private var _binding: FragmentCreatingNoteBinding? = null
    private val binding get() = _binding!!

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
        binding.saveButton.setOnClickListener {
            presenter.onAttemptSaveNote(
                binding.titleInput.text.toString(),
                binding.textInput.text.toString()
            )
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = CreatingNoteFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun saveSuccess() {
        showMessage(getString(R.string.successful_save))
    }

    override fun saveFailed() {
        showMessage(getString(R.string.save_failed))
    }

    override fun saveEmptyContent() {
        showMessage(getString(R.string.saved_empty_content))
    }

    override fun shareNote(title: String, text: String) {
        startActivity(Intent(Intent.ACTION_SEND).apply {
            type = "plain/text"
            putExtra(Intent.EXTRA_TEXT, "$title\n$text")
        })
    }

    override fun shareFailed() {
        showMessage(getString(R.string.share_failed))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share -> {
                presenter.onAttemptShareNote()
            }
        }
        return true
    }

    private fun showMessage(message: String) {
        Snackbar.make(this.requireContext(), binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}