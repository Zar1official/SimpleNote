package zar1official.simplenote.ui.main

import zar1official.simplenote.ui.main.base.MainPresenter
import zar1official.simplenote.ui.main.base.MainView

class MainPresenterImpl(var view: MainView) : MainPresenter {
    override fun onAttemptOpenNewNote() {
        view.openNewNote()
    }

    override fun onAttemptOpenNotes() {
        view.openNotes()
    }

    override fun onAttemptOpenAbout() {
        view.openAbout()
    }
}
