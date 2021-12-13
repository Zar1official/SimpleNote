package zar1official.simplenote.ui.screens.notes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import zar1official.simplenote.R
import zar1official.simplenote.databinding.NoteItemBinding
import zar1official.simplenote.model.models.Note
import zar1official.simplenote.utils.other.DateTimeUtils

class NotesAdapter(notes: List<Note>, private val clickListener: (Int) -> Unit) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    val notesList = notes as ArrayList<Note>

    inner class NoteViewHolder(noteView: View, clickAt: (Int) -> Unit) :
        RecyclerView.ViewHolder(noteView) {

        init {
            noteView.setOnClickListener {
                clickAt(absoluteAdapterPosition)
            }
        }

        val binding = NoteItemBinding.bind(noteView)
        fun bindNote(note: Note) = with(binding) {
            noteTitle.text = note.title
            noteContent.text = note.text
            noteDate.text = DateTimeUtils.millisToDateTime(note.date)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(view) {
            clickListener(it)
        }
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bindNote(notesList[position])
    }

    override fun getItemCount(): Int = notesList.size
}