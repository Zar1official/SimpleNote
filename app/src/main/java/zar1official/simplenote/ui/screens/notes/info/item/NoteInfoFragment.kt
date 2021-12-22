package zar1official.simplenote.ui.screens.notes.info.item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import zar1official.simplenote.databinding.FragmentNoteInfoBinding
import zar1official.simplenote.model.models.Note
import zar1official.simplenote.utils.other.DateTimeUtils

class NoteInfoFragment : Fragment() {
    private var _binding: FragmentNoteInfoBinding? = null
    private val binding get() = _binding!!
    private var note: Note = Note()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getArgs()
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

    private fun getArgs() {
        note = arguments?.getParcelable(DATA_PARAM) ?: note
    }

    private fun setupNote() {
        binding.run {
            note.run {
                noteTitleInfo.text = title
                noteContentInfo.text = text
                noteDateInfo.text = DateTimeUtils.millisToDateTime(date)
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