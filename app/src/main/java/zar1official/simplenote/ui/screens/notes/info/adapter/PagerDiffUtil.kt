package zar1official.simplenote.ui.screens.notes.info.adapter

import androidx.recyclerview.widget.DiffUtil
import zar1official.simplenote.domain.Note

class PagerDiffUtil(private val oldList: List<Note>, private val newList: List<Note>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldList[oldItemPosition]
        val new = newList[newItemPosition]
        return old.text == new.text && old.title == new.title && old.date == new.date
    }
}