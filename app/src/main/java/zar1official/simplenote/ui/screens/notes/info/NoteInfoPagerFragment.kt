package zar1official.simplenote.ui.screens.notes.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import zar1official.simplenote.databinding.FragmentNoteInfoPagerBinding
import zar1official.simplenote.model.models.Note
import zar1official.simplenote.ui.screens.notes.info.adapter.NotesInfoPagerAdapter
import zar1official.simplenote.ui.screens.notes.info.base.NoteInfoPagerPresenter
import zar1official.simplenote.ui.screens.notes.info.base.NoteInfoPagerView

class NoteInfoPagerFragment : Fragment(), NoteInfoPagerView {
    private var _binding: FragmentNoteInfoPagerBinding? = null
    private val binding get() = _binding!!
    private lateinit var presenter: NoteInfoPagerPresenter
    private var notesList: ArrayList<Note>? = null
    private var position: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            notesList = it?.getParcelableArrayList(NOTES_PARAM)
            position = it?.getInt(POSITION_PARAM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteInfoPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = NoteInfoPagerPresenterImpl(this)
        binding.noteInfoViewPager.adapter = NotesInfoPagerAdapter(this, notesList!!)
        binding.noteInfoViewPager.setCurrentItem(position!!, false)
    }

    companion object {
        private const val POSITION_PARAM = "position"
        private const val NOTES_PARAM = "notes"

        @JvmStatic
        fun newInstance(position: Int, notesList: ArrayList<Note>) =
            NoteInfoPagerFragment().apply {
                arguments = Bundle().apply {
                    putInt(POSITION_PARAM, position)
                    putParcelableArrayList(NOTES_PARAM, notesList)
                }
            }
    }
}