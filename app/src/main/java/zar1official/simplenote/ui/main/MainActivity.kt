package zar1official.simplenote.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import zar1official.simplenote.R
import zar1official.simplenote.databinding.ActivityMainBinding
import zar1official.simplenote.ui.screens.about.AboutFragment
import zar1official.simplenote.ui.screens.creating.CreatingNoteFragment
import zar1official.simplenote.ui.screens.notes.NotesListFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModelFactory: MainViewModelFactory
    private val viewModel: MainViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initVewModelFactory()
        subscribeViewModel()
        setNavigationListener()

        viewModel.onAttemptSetHome(savedInstanceState)
    }

    private fun initVewModelFactory() {
        viewModelFactory = MainViewModelFactory()
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

    private fun subscribeViewModel() {
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