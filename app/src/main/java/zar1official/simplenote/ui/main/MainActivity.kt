package zar1official.simplenote.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import zar1official.simplenote.R
import zar1official.simplenote.databinding.ActivityMainBinding
import zar1official.simplenote.ui.base.view.Subscriber
import zar1official.simplenote.ui.screens.about.AboutFragment
import zar1official.simplenote.ui.screens.creating.CreatingNoteFragment
import zar1official.simplenote.ui.screens.notes.NotesListFragment

class MainActivity : AppCompatActivity(), Subscriber {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        subscribeViewModel()
        setNavigationListener()

        viewModel.onAttemptSetHome(savedInstanceState)
    }

    private fun setNavigationListener() {
        binding.navView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.creatingNoteFragment -> {
                    viewModel.onAttemptOpenNewNote()
                    true
                }
                R.id.notesListFragment -> {
                    viewModel.onAttemptOpenNotes()
                    true
                }
                R.id.aboutFragment -> {
                    viewModel.onAttemptOpenAbout()
                    true
                }
                else -> {
                    false
                }
            }
        }
    }

    override fun subscribeViewModel() {
        viewModel.onOpenNewNote.observe(this) {
            openNewNote()
        }

        viewModel.onOpenNotes.observe(this) {
            openNotes()
        }

        viewModel.onOpenAbout.observe(this) {
            openAbout()
        }

        viewModel.onSetHomeFragment.observe(this) {
            setHomeFragment()
        }
    }

    private fun openNewNote() {
        switchScreen(CreatingNoteFragment())
    }

    private fun openNotes() {
        switchScreen(NotesListFragment())
    }

    private fun openAbout() {
        switchScreen(AboutFragment())
    }

    private fun switchScreen(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_wrapper, fragment).commit()
    }

    private fun setHomeFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_wrapper, CreatingNoteFragment())
            .commit()
        binding.navView.selectedItemId = R.id.creatingNoteFragment
    }
}