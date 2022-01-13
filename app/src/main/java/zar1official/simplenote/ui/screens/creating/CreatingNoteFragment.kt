package zar1official.simplenote.ui.screens.creating

import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import zar1official.simplenote.R
import zar1official.simplenote.databinding.FragmentCreatingNoteBinding
import zar1official.simplenote.domain.models.Note
import zar1official.simplenote.ui.base.view.Subscriber
import zar1official.simplenote.ui.screens.creating.dialog.ConfirmCreatingDialog
import zar1official.simplenote.ui.screens.creating.dialog.ConfirmCreatingViewModel
import zar1official.simplenote.utils.other.showSnackBar

class CreatingNoteFragment : Fragment(), Subscriber {
    private val creatingViewModel: CreatingNoteViewModel by viewModel {
        parametersOf(
            arguments?.getParcelable(DATA_PARAM) ?: Note()
        )
    }
    private val confirmCreatingViewModel: ConfirmCreatingViewModel by sharedViewModel()
    private var _binding: FragmentCreatingNoteBinding? = null
    private val binding get() = _binding!!
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        creatingViewModel.onAttemptSaveAudioUri(uri)
    }
    private val mediaPlayer: MediaPlayer by lazy {
        MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreatingNoteBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@CreatingNoteFragment
            viewmodel = creatingViewModel
        }
        setToolbarMenuItemListener()
        subscribeViewModel()
        return binding.root
    }

    companion object {
        private const val DATA_PARAM = "note_info"
        private const val TEXT_MIMETYPE = "text/plain"
        private const val MUSIC_MIMETYPE = "audio/mpeg"
        private const val TITLE_PARAM = "title"
        private const val TEXT_PARAM = "text"
        private const val DATE_PARAM = "date"
        private const val ACTION = "com.zar1official.simplenote.action_note_saved"

        @JvmStatic
        fun newInstance(note: Note): CreatingNoteFragment =
            CreatingNoteFragment().apply {
                arguments = bundleOf(
                    DATA_PARAM to note
                )
            }
    }

    override fun subscribeViewModel() {
        creatingViewModel.onSuccessfulAttemptSave.observe(this) { data ->
            showConfirmDialog(data)
        }

        creatingViewModel.onFailAttemptSave.observe(this) {
            view?.showSnackBar(R.string.saved_empty_content)
        }

        creatingViewModel.onSuccessfulAttemptShare.observe(this) { note ->
            shareNote(note)
        }

        creatingViewModel.onFailAttemptShare.observe(this) {
            view?.showSnackBar(R.string.share_failed)
        }

        creatingViewModel.onFailAttemptDownload.observe(this) {
            view?.showSnackBar(R.string.download_failed)
        }

        creatingViewModel.noteAudio.observe(this) { uri ->
            if (uri != null) {
                mediaPlayer.apply {
                    reset()
                    setDataSource(requireContext(), uri)
                    creatingViewModel.playerState.value = false
                    prepareAsync()
                }
            }
        }

        creatingViewModel.onSuccessfulAttemptPlayMusic.observe(this) { uri ->
            if (!mediaPlayer.isPlaying) {
                mediaPlayer.start()
            } else {
                mediaPlayer.pause()
            }
            creatingViewModel.onAttemptChangePlayerState()
        }

        creatingViewModel.onSuccessfulAttemptUploadMusic.observe(this) {
            launcher.launch(MUSIC_MIMETYPE)
        }

        creatingViewModel.onUnsuccessfulAttemptPlayMusic.observe(this) {
            view?.showSnackBar(R.string.playing_failed)
        }

        creatingViewModel.onSuccessfulAttemptDeleteMusic.observe(this) {
            mediaPlayer.stop()
            view?.showSnackBar(R.string.successful_note_delete)
        }

        creatingViewModel.onFailAttemptDeleteMusic.observe(this) {
            view?.showSnackBar(R.string.failed_music_delete)
        }

        confirmCreatingViewModel.onInsertSuccessfully.observe(this) { data ->
            view?.showSnackBar(R.string.successful_save)
            activity?.sendBroadcast(Intent().apply {
                action = ACTION
                putExtra(TITLE_PARAM, data.title)
                putExtra(TEXT_PARAM, data.text)
                putExtra(DATE_PARAM, data.date)
            })
        }
    }

    private fun shareNote(note: Note) {
        startActivity(Intent(Intent.ACTION_SEND).apply {
            type = TEXT_MIMETYPE
            putExtra(Intent.EXTRA_TEXT, "${note.title}\n${note.text}")
        })
    }

    private fun showConfirmDialog(note: Note) {
        ConfirmCreatingDialog.newInstance(note)
            .show(childFragmentManager, ConfirmCreatingDialog.TAG)
    }

    private fun setToolbarMenuItemListener() {
        binding.creatingNoteToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.share -> creatingViewModel.onAttemptShareNote()
                R.id.save -> creatingViewModel.onAttemptSaveNote()
                R.id.download -> creatingViewModel.onAttemptDownloadNote()
            }
            true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.reset()
    }
}