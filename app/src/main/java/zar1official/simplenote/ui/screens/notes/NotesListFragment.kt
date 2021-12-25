package zar1official.simplenote.ui.screens.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import zar1official.simplenote.R
import zar1official.simplenote.application.App
import zar1official.simplenote.databinding.FragmentNotesListBinding
import zar1official.simplenote.model.models.Note
import zar1official.simplenote.model.network.service.NoteService
import zar1official.simplenote.model.repositories.NoteRepositoryImpl
import zar1official.simplenote.model.repositories.base.NoteRepository
import zar1official.simplenote.ui.screens.notes.adapter.NotesAdapter
import zar1official.simplenote.ui.screens.notes.info.NoteInfoPagerFragment
import zar1official.simplenote.utils.mappers.NetworkNoteMapper
import zar1official.simplenote.utils.mappers.NoteMapper
import zar1official.simplenote.utils.other.showSnackBar

class NotesListFragment : Fragment() {

    private var _binding: FragmentNotesListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModelFactory: NotesListViewModelFactory
    private val viewModel: NotesListViewModel by viewModels { viewModelFactory }
    private lateinit var repository: NoteRepository
    private lateinit var noteAdapter: NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRepository()
        initViewModelFactory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeViewModel()
        viewModel.loadData()
    }

    private fun initRepository() {
        val noteDao = App.instance.db.noteDao()
        val noteService = App.instance.retrofitClient.create(NoteService::class.java)
        repository = NoteRepositoryImpl(noteDao, noteService, NoteMapper(), NetworkNoteMapper())
    }

    private fun initViewModelFactory() {
        viewModelFactory = NotesListViewModelFactory(repository)
    }

    private fun subscribeViewModel() {
        viewModel.allNotes.observe(this) { data ->
            when (data) {
                null -> {
                    view?.showSnackBar(R.string.load_notes_failed)
                }
                else -> {
                    hideProgressBar()
                    setupRecyclerAdapter(data)
                }
            }
        }
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

    private fun openNote(position: Int, notesList: ArrayList<Note>) {
        parentFragmentManager.beginTransaction().replace(
            R.id.fragment_wrapper, NoteInfoPagerFragment.newInstance(position, notesList)
        ).addToBackStack(FRAGMENT_TAG).commit()
    }

    private fun setupRecyclerAdapter(notes: List<Note>) {
        noteAdapter = NotesAdapter(notes) {
            openNote(it, noteAdapter.notesList)
        }
        binding.notesRcView.run {
            layoutManager =
                StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL)
            adapter = noteAdapter
        }
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

}