package zar1official.simplenote.ui.screens.notes.info.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import zar1official.simplenote.model.models.Note
import zar1official.simplenote.ui.screens.notes.info.item.NoteInfoFragment

class NotesInfoPagerAdapter(fragment: Fragment, private val items: ArrayList<Note>) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = items.size

    override fun createFragment(position: Int): Fragment =
        NoteInfoFragment.newInstance(items[position])
}