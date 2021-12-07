package zar1official.simplenote.ui.screens.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import zar1official.simplenote.R
import zar1official.simplenote.databinding.FragmentNotesListBinding
import zar1official.simplenote.model.Note
import zar1official.simplenote.model.repositories.NoteRepositoryImpl
import zar1official.simplenote.ui.screens.notes.adapter.NotesAdapter
import zar1official.simplenote.ui.screens.notes.base.NoteListPresenter
import zar1official.simplenote.ui.screens.notes.base.NoteListView
import zar1official.simplenote.ui.screens.notes.info.NoteInfoFragment

class NotesListFragment : Fragment(), NoteListView {

    private var _binding: FragmentNotesListBinding? = null
    private val binding get() = _binding!!
    private lateinit var presenter: NoteListPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = NoteListPresenterImpl(this@NotesListFragment, NoteRepositoryImpl())
        presenter.onLoadData()
    }

    companion object {
        private const val FRAGMENT_TAG = "NotesListFragment"
        private const val SPAN_COUNT = 2
        @JvmStatic
        fun newInstance() = NotesListFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onLoadedDataSuccessfully(data: List<Note>) {
        val noteAdapter = NotesAdapter(data) {
            presenter.onAttemptOpenNote(it)
        }
        binding.notesRcView.run {
            layoutManager =
                StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL)
            adapter = noteAdapter
        }
    }

    override fun onLoadingDataFailed() {
        showMessage(getString(R.string.load_notes_failed))
    }

    override fun openNote(note: Note) {
        parentFragmentManager.beginTransaction().replace(
            R.id.fragment_wrapper, NoteInfoFragment.newInstance(note)
        ).addToBackStack(FRAGMENT_TAG).commit()
    }

    private fun showMessage(message: String) {
        Snackbar.make(this.requireContext(), binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}