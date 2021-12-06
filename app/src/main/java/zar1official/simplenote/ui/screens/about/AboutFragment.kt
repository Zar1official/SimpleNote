package zar1official.simplenote.ui.screens.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import zar1official.simplenote.databinding.FragmentAboutBinding
import zar1official.simplenote.ui.screens.about.base.AboutPresenter
import zar1official.simplenote.ui.screens.about.base.AboutView

class AboutFragment : Fragment(), AboutView {

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!
    private lateinit var presenter: AboutPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = AboutPresenterImpl(this)
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