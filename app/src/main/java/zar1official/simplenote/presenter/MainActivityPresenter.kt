package zar1official.simplenote.presenter
import zar1official.simplenote.model.Note

class MainActivityPresenter {
    val note = Note()

    fun updateTitle(title: String){
        note.title = title
    }

    fun updateText(text: String){
        note.text= text
    }

    interface View{
        fun saveData()
    }
}