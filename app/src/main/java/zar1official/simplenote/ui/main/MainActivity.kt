package zar1official.simplenote.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import zar1official.simplenote.R
import zar1official.simplenote.databinding.ActivityMainBinding
import zar1official.simplenote.ui.main.base.MainPresenter
import zar1official.simplenote.ui.main.base.MainView
import zar1official.simplenote.ui.screens.about.AboutFragment
import zar1official.simplenote.ui.screens.creating.CreatingNoteFragment
import zar1official.simplenote.ui.screens.notes.NotesListFragment

class MainActivity : AppCompatActivity(), MainView {
    private lateinit var binding: ActivityMainBinding
    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = MainPresenterImpl(this)
        presenter.onAttemptSetHomeFragment(savedInstanceState)

        binding.navView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.creatingNoteFragment -> {
                    presenter.onAttemptOpenNewNote()
                    true
                }
                R.id.notesListFragment -> {
                    presenter.onAttemptOpenNotes()
                    true
                }
                R.id.aboutFragment -> {
                    presenter.onAttemptOpenAbout()
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    override fun openNewNote() {
        switchScreen(CreatingNoteFragment())
    }

    override fun openNotes() {
        switchScreen(NotesListFragment())
    }

    override fun openAbout() {
        switchScreen(AboutFragment())
    }

    private fun switchScreen(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_wrapper, fragment).commit()
    }

    override fun setHomeFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_wrapper, CreatingNoteFragment())
            .commit()
        binding.navView.selectedItemId = R.id.creatingNoteFragment
    }
}