package zar1official.simplenote.ui.screens.notes.info.item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import zar1official.simplenote.databinding.FragmentNoteInfoBinding
import zar1official.simplenote.model.models.Note
import zar1official.simplenote.utils.other.DateTimeUtils

class NoteInfoFragment : Fragment() {
    private var _binding: FragmentNoteInfoBinding? = null
    private val binding get() = _binding!!
    private var note: Note? = null
    private lateinit var viewModelFactory: NoteInfoViewModelFactory
    private val viewModel: NoteInfoViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getArgs()
        initViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNote()
    }

    private fun initViewModelFactory() {
        viewModelFactory = NoteInfoViewModelFactory()
    }

    private fun getArgs() {
        arguments.let {
            note = it?.getParcelable(DATA_PARAM) ?: throw IllegalArgumentException()
        }
    }

    private fun setupNote() {
        binding.run {
            note?.let {
                noteTitleInfo.text = it.title
                noteContentInfo.text = it.text
                noteDateInfo.text = DateTimeUtils.millisToDateTime(it.date)
            }
        }
    }

    companion object {
        private const val DATA_PARAM = "note_info"
        @JvmStatic
        fun newInstance(note: Note): NoteInfoFragment =
            NoteInfoFragment().apply {
                arguments = bundleOf(
                    DATA_PARAM to note
                )
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}