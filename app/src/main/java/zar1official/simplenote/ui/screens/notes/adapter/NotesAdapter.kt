package zar1official.simplenote.ui.screens.notes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import zar1official.simplenote.R
import zar1official.simplenote.databinding.NoteItemBinding
import zar1official.simplenote.domain.models.Note
import zar1official.simplenote.ui.base.adapter.Adapter
import zar1official.simplenote.ui.base.adapter.AdapterEventListener
import zar1official.simplenote.utils.other.DateTimeUtils


class NotesAdapter(
    private val eventListener: AdapterEventListener,
) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>(), Adapter {

    val notesList = ArrayList<Note>()

    inner class NoteViewHolder(noteView: View, clickAt: (Int) -> Unit) :
        RecyclerView.ViewHolder(noteView) {

        init {
            noteView.setOnClickListener {
                clickAt(absoluteAdapterPosition)
            }
        }

        private val binding = NoteItemBinding.bind(noteView)

        fun bindNote(note: Note) = with(binding) {
            note.run {
                noteTitle.text = title
                noteContent.text = text
                noteDate.text = DateTimeUtils.millisToDateTime(note.date)
            }
            root.contentDescription = prepareContentDescription(note)
        }

        private fun prepareContentDescription(note: Note): String =
            "${binding.root.resources.getString(R.string.note_item_description)}${note.title}. ${note.text}"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(view) {
            eventListener.onClick(it)
        }
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bindNote(notesList[position])
    }

    override fun getItemCount(): Int = notesList.size

    fun onSwipe(position: Int) {
        eventListener.onSwipe(notesList[position])
    }

    override fun updateData(newList: List<Note>) {
        val diffCallback = NotesDiffUtil(notesList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        notesList.clear()
        notesList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }
}