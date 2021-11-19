package zar1official.simplenote.presenter
import zar1official.simplenote.model.Note
import zar1official.simplenote.view.NoteView

class MainActivityPresenter(val view: NoteView) {
    val note = Note()

    private fun updateTitle(title: String){
        note.title = title
    }

    private fun updateText(text: String){
        note.text= text
    }

    fun tryToSaveNote(title: String, text: String){
        if (title.isNotEmpty() && text.isNotEmpty()){
            updateTitle(title)
            updateText(text)
            view.saveSuccess()
        }else{
            view.saveEmptyContent()
        }
    }
}