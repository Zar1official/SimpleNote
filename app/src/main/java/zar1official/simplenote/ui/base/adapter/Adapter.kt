package zar1official.simplenote.ui.base.adapter

import zar1official.simplenote.domain.Note

interface Adapter {
    fun updateData(newList: List<Note>)
}