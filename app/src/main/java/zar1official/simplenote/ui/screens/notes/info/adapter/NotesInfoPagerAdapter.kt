package zar1official.simplenote.ui.screens.notes.info.adapter

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.viewpager2.adapter.FragmentStateAdapter
import zar1official.simplenote.domain.Note
import zar1official.simplenote.ui.screens.creating.CreatingNoteFragment

class NotesInfoPagerAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {

    private val items = ArrayList<Note>()

    override fun getItemCount(): Int = items.size

    override fun createFragment(position: Int): Fragment {
        return CreatingNoteFragment.newInstance(items[position])
    }

    fun updateData(newList: List<Note>) {
        val diffCallback = PagerDiffUtil(items, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items.clear()
        items.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }
}