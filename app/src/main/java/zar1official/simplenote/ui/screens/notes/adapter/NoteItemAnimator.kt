package zar1official.simplenote.ui.screens.notes.adapter

import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import zar1official.simplenote.R

class NoteItemAnimator : DefaultItemAnimator() {
    override fun animateAdd(holder: RecyclerView.ViewHolder?): Boolean {
        holder?.itemView?.animation =
            AnimationUtils.loadAnimation(holder?.itemView?.context, R.anim.list_anim)
        return super.animateAdd(holder)
    }
}