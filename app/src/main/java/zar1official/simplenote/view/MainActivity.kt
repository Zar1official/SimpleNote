package zar1official.simplenote.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import zar1official.simplenote.databinding.ActivityMainBinding
import zar1official.simplenote.presenter.MainActivityPresenter

class MainActivity : AppCompatActivity(), MainActivityPresenter.View {
    lateinit var binding: ActivityMainBinding
    lateinit var presenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = MainActivityPresenter()

        binding.saveButton.setOnClickListener {
            saveData()
        }
    }

    override fun saveData() {
        presenter.updateTitle(binding.titleInput.text.toString())
        presenter.updateText(binding.titleInput.text.toString())
        Snackbar.make(this, binding.root, "Saved successfully", Snackbar.LENGTH_SHORT).show()
    }
}