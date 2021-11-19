package zar1official.simplenote.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import zar1official.simplenote.R
import zar1official.simplenote.databinding.ActivityMainBinding
import zar1official.simplenote.presenter.MainActivityPresenter

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

    private fun showMessage(message: String) {
        Snackbar.make(this, binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}