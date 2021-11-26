package zar1official.simplenote.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import zar1official.simplenote.R
import zar1official.simplenote.databinding.ActivityMainBinding
import zar1official.simplenote.presenter.MainActivityPresenter
import zar1official.simplenote.base.NoteView

class MainActivity : AppCompatActivity(), NoteView {
    lateinit var binding: ActivityMainBinding
    lateinit var presenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = MainActivityPresenter(this)

        binding.saveButton.setOnClickListener {
            presenter.tryToSaveNote(
                binding.titleInput.text.toString(),
                binding.textInput.text.toString()
            )
        }
    }

    override fun saveSuccess() {
        showMessage(getString(R.string.successful_save))
    }

    override fun saveFailed() {
        showMessage(getString(R.string.save_failed))
    }

    override fun saveEmptyContent() {
        showMessage(getString(R.string.saved_empty_content))
    }

    override fun shareNote(title: String, text: String) {
        startActivity(Intent(Intent.ACTION_SEND).apply {
            type = "plain/text"
            putExtra(Intent.EXTRA_TEXT, "$title\n$text")
        })
    }

    override fun shareFailed() {
        showMessage(getString(R.string.share_failed))
    }

    override fun openAbout() {
        startActivity(Intent(this, AboutActivity::class.java))
    }

    private fun showMessage(message: String) {
        Snackbar.make(this, binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share -> {
                presenter.tryToShareNote()
            }
            R.id.about -> {
                presenter.tryToOpenAbout()
            }
        }
        return true
    }
}