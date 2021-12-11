package zar1official.simplenote.ui.screens.notes.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import zar1official.simplenote.databinding.FragmentNoteInfoBinding
import zar1official.simplenote.model.models.Note
import zar1official.simplenote.ui.screens.notes.info.base.NoteInfoPresenter
import zar1official.simplenote.ui.screens.notes.info.base.NoteInfoView
import zar1official.simplenote.utils.other.DateTimeUtils

class NoteInfoFragment : Fragment(), NoteInfoView {
    lateinit var presenter: NoteInfoPresenter
    private var _binding: FragmentNoteInfoBinding? = null
    private val binding get() = _binding!!
    private var note: Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            note = it?.getParcelable(DATA_PARAM)
        }
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
        presenter = NoteInfoPresenterImpl(this)
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