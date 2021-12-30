package zar1official.simplenote.ui.screens.notes.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import zar1official.simplenote.application.App
import zar1official.simplenote.data.mappers.NetworkNoteMapper
import zar1official.simplenote.data.mappers.NoteMapper
import zar1official.simplenote.data.network.service.NoteService
import zar1official.simplenote.data.repositories.NoteRepositoryImpl
import zar1official.simplenote.databinding.FragmentNoteInfoPagerBinding
import zar1official.simplenote.domain.NoteRepository
import zar1official.simplenote.ui.screens.notes.NotesListViewModel
import zar1official.simplenote.ui.screens.notes.NotesListViewModelFactory
import zar1official.simplenote.ui.screens.notes.info.adapter.NotesInfoPagerAdapter
import zar1official.simplenote.utils.other.observeOnce

class NoteInfoPagerFragment : Fragment() {
    private var _binding: FragmentNoteInfoPagerBinding? = null
    private val binding get() = _binding!!
    private var position: Int = 0
    private lateinit var repository: NoteRepository
    private val viewModel: NotesListViewModel by viewModels { NotesListViewModelFactory(repository) }
    private lateinit var adapter: NotesInfoPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getArgs()
        initRepository()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteInfoPagerBinding.inflate(inflater, container, false).apply {
            adapter = NotesInfoPagerAdapter(this@NoteInfoPagerFragment)
            noteInfoViewPager.adapter = adapter
            viewModel.allNotes.observeOnce(this@NoteInfoPagerFragment) { notes ->
                adapter.updateData(notes)
                noteInfoViewPager.setCurrentItem(position, false)
                hideProgressBar()
            }
            viewModel.allNotes.observe(this@NoteInfoPagerFragment) { notes ->
                adapter.updateData(notes)
            }
        }
        return binding.root
    }

    private fun getArgs() {
        arguments?.let {
            position = it.getInt(POSITION_PARAM)
        }
    }

    private fun initRepository() {
        val noteDao = App.instance.db.noteDao()
        val noteService = App.instance.retrofitClient.create(NoteService::class.java)
        repository = NoteRepositoryImpl(noteDao, noteService, NoteMapper(), NetworkNoteMapper())
    }

    companion object {
        private const val POSITION_PARAM = "position"

        @JvmStatic
        fun newInstance(position: Int) =
            NoteInfoPagerFragment().apply {
                arguments = Bundle().apply {
                    putInt(POSITION_PARAM, position)
                }
            }
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}