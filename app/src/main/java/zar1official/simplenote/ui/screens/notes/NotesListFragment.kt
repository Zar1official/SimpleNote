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
import zar1official.simplenote.data.mappers.NetworkNoteMapper
import zar1official.simplenote.data.mappers.NoteMapper
import zar1official.simplenote.data.network.service.NoteService
import zar1official.simplenote.data.repositories.NoteRepositoryImpl
import zar1official.simplenote.databinding.FragmentNotesListBinding
import zar1official.simplenote.domain.NoteRepository
import zar1official.simplenote.ui.screens.notes.adapter.NotesAdapter
import zar1official.simplenote.ui.screens.notes.info.NoteInfoPagerFragment
import zar1official.simplenote.utils.other.observeOnce
import zar1official.simplenote.utils.other.showSnackBar

class NotesListFragment : Fragment() {

    private var _binding: FragmentNotesListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NotesListViewModel by viewModels { NotesListViewModelFactory(repository) }
    private lateinit var repository: NoteRepository
    private lateinit var noteAdapter: NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesListBinding.inflate(inflater, container, false).apply {
            viewModel.allNotes.observeOnce(this@NotesListFragment) { data ->
                when (data) {
                    null -> {
                        view?.showSnackBar(R.string.load_notes_failed)
                    }
                    else -> {
                        hideProgressBar()
                        noteAdapter = NotesAdapter(data) { position ->
                            openNote(position)
                        }
                        notesRcView.run {
                            layoutManager =
                                StaggeredGridLayoutManager(
                                    SPAN_COUNT,
                                    StaggeredGridLayoutManager.VERTICAL
                                )
                            adapter = noteAdapter
                        }
                    }
                }
            }
        }
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRepository()
    }

    private fun initRepository() {
        val noteDao = App.instance.db.noteDao()
        val noteService = App.instance.retrofitClient.create(NoteService::class.java)
        repository = NoteRepositoryImpl(noteDao, noteService, NoteMapper(), NetworkNoteMapper())
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

    private fun openNote(position: Int) {
        parentFragmentManager.beginTransaction().replace(
            R.id.fragment_wrapper, NoteInfoPagerFragment.newInstance(position)
        ).addToBackStack(FRAGMENT_TAG).commit()
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }
}