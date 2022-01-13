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
    private val position: Int by lazy { arguments?.getInt(POSITION_PARAM) ?: 0 }
    private val filter: String? by lazy { arguments?.getString(FILTER_PARAM) }
    private val viewModel: NotesListViewModel by sharedViewModel()
    private lateinit var adapter: NotesInfoPagerAdapter

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

    companion object {
        private const val POSITION_PARAM = "position"
        private const val FILTER_PARAM = "filter"

        @JvmStatic
        fun newInstance(position: Int, filter: String?) =
            NoteInfoPagerFragment().apply {
                arguments = Bundle().apply {
                    putInt(POSITION_PARAM, position)
                    putString(FILTER_PARAM, filter)
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

        viewModel.allNotes.observe(this) { data ->
            viewModel.onAttemptUpdateNotes(data)
        }

        viewModel.currentNoteList.observe(this) {
            viewModel.onAttemptUpdateFilter(filter)
        }

        viewModel.noteFilter.observe(this) {
            viewModel.onAttemptUpdateFilteredList()
        }

        viewModel.currentFilteredNoteList.observeOnce(this) { data ->
            adapter.updateData(data)
            binding.noteInfoViewPager.setCurrentItem(position, false)
            hideProgressBar()
        }

        viewModel.currentFilteredNoteList.observe(this) { data ->
            adapter.updateData(data)
        }
    }

}