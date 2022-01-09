package zar1official.simplenote.ui.screens.notes.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import zar1official.simplenote.databinding.FragmentNoteInfoPagerBinding
import zar1official.simplenote.ui.base.view.Subscriber
import zar1official.simplenote.ui.screens.notes.NotesListViewModel
import zar1official.simplenote.ui.screens.notes.info.adapter.NotesInfoPagerAdapter
import zar1official.simplenote.ui.screens.notes.info.adapter.NotesPageTransformer
import zar1official.simplenote.utils.other.observeOnce

class NoteInfoPagerFragment : Fragment(), Subscriber {
    private var _binding: FragmentNoteInfoPagerBinding? = null
    private val binding get() = _binding!!
    private var position: Int = 0
    private val viewModel: NotesListViewModel by sharedViewModel()
    private lateinit var adapter: NotesInfoPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteInfoPagerBinding.inflate(inflater, container, false).apply {
            adapter = NotesInfoPagerAdapter(this@NoteInfoPagerFragment)
            noteInfoViewPager.adapter = adapter
            noteInfoViewPager.setPageTransformer(NotesPageTransformer())
        }
        subscribeViewModel()
        return binding.root
    }

    private fun getArgs() {
        arguments?.let {
            position = it.getInt(POSITION_PARAM)
        }
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

    override fun subscribeViewModel() {
        viewModel.allNotes.observeOnce(this@NoteInfoPagerFragment) { notes ->
            adapter.updateData(notes)
            binding.noteInfoViewPager.setCurrentItem(position, false)
            hideProgressBar()
        }
        viewModel.allNotes.observe(this@NoteInfoPagerFragment) { notes ->
            adapter.updateData(notes)
        }
    }

}