package zar1official.simplenote.ui.screens.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import zar1official.simplenote.R
import zar1official.simplenote.databinding.FragmentNotesListBinding
import zar1official.simplenote.domain.models.Note
import zar1official.simplenote.ui.base.adapter.AdapterEventListener
import zar1official.simplenote.ui.base.view.Subscriber
import zar1official.simplenote.ui.screens.notes.adapter.NoteTouchHelper
import zar1official.simplenote.ui.screens.notes.adapter.NotesAdapter
import zar1official.simplenote.ui.screens.notes.info.NoteInfoPagerFragment
import zar1official.simplenote.utils.other.observeOnce
import zar1official.simplenote.utils.other.showSnackBar

class NotesListFragment : Fragment(), Subscriber {

    private var _binding: FragmentNotesListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NotesListViewModel by viewModel()
    private lateinit var noteAdapter: NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesListBinding.inflate(inflater, container, false).apply {
            noteAdapter = NotesAdapter(object : AdapterEventListener {
                override fun onClick(position: Int) {
                    viewModel.onAttemptOpenNote(position)
                }

                override fun onSwipe(note: Note) {
                    viewModel.onAttemptRemoveNote(note)
                }
            })

            notesRcView.apply {
                layoutManager =
                    StaggeredGridLayoutManager(
                        SPAN_COUNT,
                        StaggeredGridLayoutManager.VERTICAL
                    )
                adapter = noteAdapter
                ItemTouchHelper(NoteTouchHelper(noteAdapter)).attachToRecyclerView(this)
            }

            searchNotes.setOnQueryTextListener(object :
                SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean = false

                override fun onQueryTextChange(newText: String?): Boolean {
                    viewModel.onAttemptUpdateFilter(newText)
                    return true
                }
            })
        }
        subscribeViewModel()
        return binding.root
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

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

    override fun subscribeViewModel() {

        viewModel.onDeleteNoteSuccessfully.observe(this) {
            view?.showSnackBar(R.string.successful_note_delete)
        }

        viewModel.onOpenNoteSuccessfully.observeOnce(this) { data ->
            parentFragmentManager.beginTransaction().replace(
                R.id.fragment_wrapper, NoteInfoPagerFragment.newInstance(data.first, data.second)
            ).addToBackStack(FRAGMENT_TAG).commit()
        }

        viewModel.allNotes.observeOnce(this) {
            hideProgressBar()
        }

        viewModel.allNotes.observe(this) { data ->
            viewModel.onAttemptUpdateNotes(data)
        }

        viewModel.currentNoteList.observe(this) { data ->
            viewModel.onAttemptUpdateFilteredList()
        }

        viewModel.noteFilter.observe(this) {
            viewModel.onAttemptUpdateFilteredList()
        }

        viewModel.currentFilteredNoteList.observe(this) { data ->
            noteAdapter.updateData(data)
        }
    }
}