package zar1official.simplenote.ui.screens.creating

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import zar1official.simplenote.R
import zar1official.simplenote.application.App
import zar1official.simplenote.databinding.FragmentCreatingNoteBinding
import zar1official.simplenote.model.models.Note
import zar1official.simplenote.model.network.service.NoteService
import zar1official.simplenote.model.repositories.NoteRepositoryImpl
import zar1official.simplenote.model.repositories.base.NoteRepository
import zar1official.simplenote.ui.screens.creating.dialog.ConfirmCreatingDialog
import zar1official.simplenote.utils.mappers.NetworkNoteMapper
import zar1official.simplenote.utils.mappers.NoteMapper
import zar1official.simplenote.utils.other.showSnackBar

class CreatingNoteFragment : Fragment() {

    private lateinit var viewModelFactory: CreatingNoteViewModelFactory
    private val viewModel: CreatingNoteViewModel by viewModels { viewModelFactory }
    private var _binding: FragmentCreatingNoteBinding? = null
    private val binding get() = _binding!!
    private lateinit var repository: NoteRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreatingNoteBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@CreatingNoteFragment
            viewmodel = viewModel
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = CreatingNoteFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRepository()
        initViewModelFactory()
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeViewModel()
    }

    private fun initViewModelFactory() {
        viewModelFactory = CreatingNoteViewModelFactory(repository)
    }

    private fun initRepository() {
        val noteDao = App.instance.db.noteDao()
        val noteService = App.instance.retrofitClient.create(NoteService::class.java)
        repository = NoteRepositoryImpl(noteDao, noteService, NoteMapper(), NetworkNoteMapper())
    }

    private fun subscribeViewModel() {
        viewModel.onSuccessfulAttemptSave.observe(this) { note ->
            showConfirmDialog(note)
        }

        viewModel.onFailAttemptSave.observe(this) {
            view?.showSnackBar(R.string.saved_empty_content)
        }

        viewModel.onSuccessfulAttemptShare.observe(this) { note ->
            shareNote(note)
        }

        viewModel.onFailAttemptShare.observe(this) {
            view?.showSnackBar(R.string.share_failed)
        }

        viewModel.onFailAttemptDownload.observe(this) {
            view?.showSnackBar(R.string.download_failed)
        }
    }

    private fun shareNote(note: Note) {
        startActivity(Intent(Intent.ACTION_SEND).apply {
            type = "plain/text"
            putExtra(Intent.EXTRA_TEXT, "${note.title}\n${note.text}")
        })
    }

    private fun showConfirmDialog(note: Note) {
        ConfirmCreatingDialog.newInstance(note)
            .show(childFragmentManager, ConfirmCreatingDialog.TAG)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share -> {
                viewModel.onAttemptShareNote()
            }
            R.id.save -> {
                viewModel.onAttemptSaveNote()
            }

            R.id.download -> {
                viewModel.onAttemptDownloadNote()
            }
        }
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}