package zar1official.simplenote.ui.screens.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import zar1official.simplenote.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModelFactory: AboutViewModelFactory
    private val viewModel: AboutViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModelFactory()
    }

    private fun initViewModelFactory() {
        viewModelFactory = AboutViewModelFactory()
    }

    companion object {
        @JvmStatic
        fun newInstance() = AboutFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}