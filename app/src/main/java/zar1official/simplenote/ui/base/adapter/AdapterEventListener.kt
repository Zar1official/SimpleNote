package zar1official.simplenote.ui.base.adapter

import zar1official.simplenote.domain.models.Note

interface AdapterEventListener {
    fun onClick(position: Int)
    fun onSwipe(note: Note)
}